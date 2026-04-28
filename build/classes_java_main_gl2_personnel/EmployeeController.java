package gl2.example.personnel.controller;

import gl2.example.personnel.model.Employee;
import gl2.example.personnel.service.EmployeeService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/employees"})
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return this.employeeService.getAllEmployees();
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = this.employeeService.getEmployeeById(id);
        return (ResponseEntity)employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        return this.employeeService.addEmployee(employee);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        this.employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Optional<Employee> existing = this.employeeService.getEmployeeById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            employee.setId(id);
            return ResponseEntity.ok(this.employeeService.addEmployee(employee));
        }
    }

    @GetMapping({"/salary/max"})
    public Employee getHighestSalary() {
        return this.employeeService.getHighestSalaryEmployee();
    }

    @GetMapping({"/salary/average"})
    public Double getAverageSalary() {
        return this.employeeService.getAverageSalary();
    }

    @GetMapping({"/raise/global"})
    public ResponseEntity<String> raiseAllSalaries() {
        this.employeeService.giveGlobalRaise();
        return ResponseEntity.ok("Tous les salaires ont été augmentés de 10% !");
    }

    @PatchMapping({"/{id}"})
    public ResponseEntity<Employee> patchEmployee(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Employee updated = this.employeeService.updateEmployeePartial(id, updates);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }
}
