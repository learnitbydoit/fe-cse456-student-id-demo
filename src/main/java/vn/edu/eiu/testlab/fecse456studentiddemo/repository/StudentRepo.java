package vn.edu.eiu.testlab.fecse456studentiddemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.eiu.testlab.fecse456studentiddemo.model.Student;
/** keyword : Derived query methods in spring data jpa*/
@Repository
public interface StudentRepo extends JpaRepository<Student,String> {
    //Kế thừa các hàm abstract tự sinh để truy xuất db, không cần viết gì thêm.
}
