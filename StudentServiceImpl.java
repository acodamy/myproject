package com.student.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.student.entities.Address;
import com.student.entities.Course;
import com.student.entities.Guide;
import com.student.entities.Student;
import com.student.repositories.AddressRepository;
import com.student.repositories.CourseRepository;
import com.student.repositories.GuideRepository;
import com.student.repositories.StudentRepository;

//create student service to insert,delete,update,retrieve data into student table
@Service
@Component
public class StudentServiceImpl implements StudentService {
	/*
	 * @Autowired Marks a constructor or field or setter method as to be autowired
	 * by Spring's dependency injection facilities.
	 */
	
	@Autowired
	FileUploadService fileUploadService;

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	CourseRepository courseRepository;
	@Autowired
	GuideRepository guideRepository;

	public void setStudentRepository(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	// read excel data using apache poi
	public static HashMap getDataFromExcel() {

		// Used the LinkedHashMap and LikedList to maintain the order
		HashMap<String, LinkedHashMap<Integer, List>> outerMap = new LinkedHashMap<String, LinkedHashMap<Integer, List>>();

		LinkedHashMap<Long, String> hashMap = new LinkedHashMap<Long, String>();

		// String sheetName = null;
		// Create an ArrayList to store the data read from excel sheet.
		// List sheetData = new ArrayList();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File("C://Users//Karnati.jyothsna//Downloads/SampleExcel.xls"));
			// read an excel workbook from the file system
			@SuppressWarnings("resource")
			HSSFWorkbook workBook = new HSSFWorkbook(fis);

			for (int i = 0; i < workBook.getNumberOfSheets(); i++) {
				// HSSFSheet sheet = workBook.getSheetAt(i);
				// Get the first sheet on the workbook.
				HSSFSheet sheet = workBook.getSheetAt(0);
				// sheetName = workBook.getSheetName(0);

				Iterator rows = sheet.rowIterator();
					HSSFRow row = (HSSFRow) rows.next();
					if(row.getRowNum()>0) {
					Iterator cells = row.cellIterator();

					//List data = new LinkedList();
					Long cellno;
					while (cells.hasNext()) {
						HSSFCell cell = (HSSFCell) cells.next();
						//cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellvalue = cell.getStringCellValue();
						//data.add(cell);
						cellno = (long) cell.getColumnIndex();
						hashMap.put(cellno, cellvalue);
					}
					// hashMap.put(cellno, data);

					// sheetData.add(data);
				}
				// outerMap.put(sheetName, hashMap);
				// hashMap = new LinkedHashMap<Integer, List>();
			}

			Iterator itr = hashMap.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry map = (Entry) itr.next();
				System.out.println(map.getKey() + " " + map.getValue());
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return hashMap;
	}

	// to insert and save student data into student table
	public Student addStudent(Student student) {
		Student studentDetails = new Student();
		Address adres1 = new Address();
		HashMap map=getDataFromExcel();
		
		Iterator itr = map.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry map1 = (Entry) itr.next();
			System.out.println(map1.getKey() + " " + map1.getValue());
            if((Long) map1.getKey()==0 && !(map1.getValue() instanceof String)) {
            	studentDetails.setId((Long)map1.getValue());
            } else  if((Long) map1.getKey()==1) {
            	studentDetails.setName((String)map1.getValue());
            }else  if((Long) map1.getKey()==2) {
            	adres1.setCity((String)map1.getValue());
            }		
		}
		
		
		//studentDetails.setName(student.getName());
		
		
		// adres1.getStudent().add(student);
		/// adres1.setStudent(address.getStudent());
		//addressRepository.save(adres1);
		//studentDetails.setAddress(student.getAddress());
		/*Course course=new Course();
		Guide guide=new Guide();
		guideRepository.save(guide);
		courseRepository.save(course);
		studentDetails.setCourses(student.getCourses());
		studentDetails.setGuides(student.getGuides());*/
		return studentRepository.save(studentDetails);

	}

	// to get student data from student table
	public List<Student> getAllStudentData() {
		return (List<Student>) studentRepository.findAll();
	}
	// get student data by using studentId

	public List<Student> findStudentByid(Long id) {
		List<Student> studentdata = new ArrayList<>();
		studentdata.add(studentRepository.findById(id).get());
		return studentdata;
	}

	// to delete student data from student table
	public void deleteStudentDataById(Long id) {
		Optional<Student> student = studentRepository.findById(id);
		if (student.isPresent()) {
			Student student1 = student.get();
			studentRepository.delete(student1);
		}
	}

	public Student updateStudentById(Long id, Student stDetails) {
		// TODO Auto-generated method stub
		Optional<Student> student = studentRepository.findById(id);
		if (student.isPresent()) {
			Student student1 = student.get();
			student1.setName(stDetails.getName());
			student1.setAddress(stDetails.getAddress());
			studentRepository.save(student1);
		}
		return stDetails;

	}

	@Override
	public Address addAddress(Address address) {
		Address adres1 = new Address();
		adres1.setCity(address.getCity());
		adres1.setStudent(address.getStudent());
		return addressRepository.save(adres1);
	}

}
