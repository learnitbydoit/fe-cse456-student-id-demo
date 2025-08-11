package vn.edu.eiu.testlab.fecse456studentiddemo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_Student")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {
    @Id
    @Column(name = "ID", columnDefinition = "CHAR(5)")
    private String id;

    @Column(name = "Name",nullable = false,columnDefinition = "NVARCHAR(100)")
    private String name;

    @Column(name = "Year of Birth",nullable = false)
    private int yob;

    @Column(name = "GPA")
    private double gpa;

    @ManyToOne()
    @JoinColumn(name = "majorID")//Để báo là tui muốn đặt tên cột khóa ngoại
    private Major major; //Cái này sẽ gắn vào mappedBy bên class Major

    //Bổ sung thêm constructor không có major, vì lúc thêm mới sinh viên chưa có thông tin major.
    public Student(String id, String name, int yob, double gpa) {
        this.id = id;
        this.name = name;
        this.yob = yob;
        this.gpa = gpa;
    }
}
