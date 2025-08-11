package vn.edu.eiu.testlab.fecse456studentiddemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eiu.testlab.fecse456studentiddemo.model.Major;
import vn.edu.eiu.testlab.fecse456studentiddemo.model.Student;
import vn.edu.eiu.testlab.fecse456studentiddemo.service.MajorService;
import vn.edu.eiu.testlab.fecse456studentiddemo.service.StudentService;

import java.util.List;

/** UI -- Controller(Đang ở đây) -- Service -- Repository -- [(JDBC) -- DB(Mysql)]
 * mỗi một url thì cần phải có một hàm xử lý
 * Đối với form thì dùng @PostMapping*/
@Controller
public class StudentController {
    //Tiêm service để truy xuất db
    @Autowired //tiêm bằng field
    private StudentService studentServ;
    @Autowired
    private MajorService majorServ;

    //url localhost:8080/students --> trả về trang student-list.html
    @GetMapping("students")
    public String students(Model model) {
        //Lấy danh sách sinh viên tự db
        List<Student> studentList = studentServ.getAllStudents();

        //Gửi qua view cho thymeleaf mix với html tag
        model.addAttribute("studentList", studentList);

        return "student-list"; //student-list.html
    }

    //Hàm xử lý url: localhost:8080/student/edit/{id}
    @GetMapping("student/edit/{id}")
    public String editStudent(@PathVariable("id") String id, Model model) {
        //Lấy sinh viên viên từ db có mã là id.
        Student s = studentServ.getStudentById(id);

        //Gửi qua cho form chỉnh sửa
        model.addAttribute("student", s);

        //Gửi thêm danh sách major để làm select (comboBox)
        List<Major> majorList = majorServ.getAllMajors();
        model.addAttribute("majorList", majorList);

        return "student-form"; //Trả về trang form chỉnh sửa thông tin sinh viên.
    }

    //Hàm xử lý cho url /student/form, khi người dùng bấm save trên form bằng Post method
    @PostMapping("/student/form")
    public String saveStudent(@ModelAttribute("student") Student s) {
        //Lấy thông tin gửi từ form xuống
        studentServ.saveStudent(s);
        //Validate coi thông tin có hợp lệ không
        //Lưu xuống database
        //Trả về url students để hiển thị danh sách sinh viên đã cập nhật bằng redirect
        return "redirect:/students";

    }


}
