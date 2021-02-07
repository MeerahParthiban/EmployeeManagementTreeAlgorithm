import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class EmployeeManagementTree {
	static int depth =1;
	public static void main(String[] args) {
		//Creating Employee list 
		Employee emp = new Employee(10,"Tom",0);
		Employee emp1 = new Employee(2,"Mikey",10);
		Employee emp2 = new Employee(3,"Jerry",10);
		Employee emp3 = new Employee(7,"John",2);
		Employee emp4 = new Employee(5,"Sarah",10);
		List<Employee> employeeList = new ArrayList<>();
		employeeList.add(emp);
		employeeList.add(emp1);
		employeeList.add(emp2);
		employeeList.add(emp3);
		employeeList.add(emp4);

		displayManagementTree(employeeList);
	}

	//Method to display Employee Management Tree
	public static void displayManagementTree(List<Employee> employeeList){

		HashMap<Integer,ArrayList<String>> employeeMap=new HashMap<Integer,ArrayList<String>>();
		List<Employee> subEmployeeList= new ArrayList<>();
		int employeeId = 0;
		String arrow = "";

		/**Adding employeeId and their corresponding sub-employees,
		 *  who reports to them in a HashMap
		 */
		for(Employee employee :employeeList) {
			subEmployeeList = getSubEmployee(employee.getId(),employeeList);
			ArrayList<String> employeeNameList = new ArrayList<>();
			if(subEmployeeList!=null){
				for(Employee emp: subEmployeeList) {
					employeeNameList.add(emp.getName());
				}
			}   employeeMap.put(employee.getId(), employeeNameList);
		}

		//Displaying root manager
		for(Employee employee :employeeList) {
			if(employee.getManagerId()==0) {
				employeeId = employee.getId();
				System.out.println("->"+employee.getName());
			}		
		}

		//Displaying other employees in the hierarchy
		ArrayList<String> subEmployeeNameList = employeeMap.get(employeeId);
		ListIterator<String> listIterator= subEmployeeNameList.listIterator();
	    boolean booleanValue = false;
		
		if(subEmployeeNameList!=null){
		   depth++;
		   while(listIterator.hasNext()){
				String employeeName =listIterator.next();
                for(int i=1;i<=depth;i++) arrow+="->";
				System.out.println(arrow+employeeName);
				arrow="";
					
				if(booleanValue==true) {
					  depth--;
					  booleanValue = false;
				}
				for(Employee employee :employeeList) {
				   if(employee.getName().equals(employeeName)){
						employeeId = employee.getId();
					}
				}
				ArrayList<String> employeeSubList = employeeMap.get(employeeId);
				if(employeeSubList!=null) {
				  Collections.reverse(employeeSubList);
				  for(String eName: employeeSubList)
					{       
					  listIterator.add(eName);
					  depth++;
					  booleanValue = true;
					  listIterator.previous();
					}
				 }
				}
		   }
  }
  
	//Method to get Sorted SubEmployees List who report to given employeeId
	public static List<Employee> getSubEmployee(int employeeId,List<Employee> employeeList) {
		List<Employee> subEmployeeList = new ArrayList<>();
		for(Employee employee :employeeList) {
			if(employee.getManagerId()==employeeId) {
				subEmployeeList.add(employee);
			}
		}
		if(subEmployeeList.isEmpty())
			return null;
		return subEmployeeList.stream()
				.sorted(Comparator.comparing(Employee::getName))
				.collect(Collectors.toList());
	}

}