package gl2.example.personnel.repository;

import gl2.example.personnel.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findFirstByOrderBySalaryDesc();

    @Query("SELECT AVG(e.salary) FROM Employee e")
    Double getAverageSalary();
}
