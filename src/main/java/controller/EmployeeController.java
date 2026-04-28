package gl2.example.personnel.controller;

import gl2.example.personnel.model.Employee;
import gl2.example.personnel.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Optional<Employee> existing = employeeService.getEmployeeById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();
        employee.setId(id);
        return ResponseEntity.ok(employeeService.addEmployee(employee));
    }

    @GetMapping("/salary/max")
    public Employee getHighestSalary() {
        return employeeService.getHighestSalaryEmployee();
    }

    @GetMapping("/salary/average")
    public Double getAverageSalary() {
        return employeeService.getAverageSalary();
    }

    @GetMapping("/raise/global")
    public ResponseEntity<String> raiseAllSalaries() {
        employeeService.giveGlobalRaise();
        return ResponseEntity.ok("Tous les salaires ont été augmentés de 10% !");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Employee> patchEmployee(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Employee updated = employeeService.updateEmployeePartial(id, updates);
        return (updated != null) ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }
}
