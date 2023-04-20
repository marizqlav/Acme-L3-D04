
package acme.features.student.activity;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.activities.Activity;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;

@Controller
public class StudentActivityController extends AbstractController<Student, Activity> {

	@Autowired
	StudentActivityListService				listService;

	@Autowired
	StudentActivityShowService				showService;

	@Autowired
	protected StudentActivityCreateService	createService;

	@Autowired
	protected StudentActivityUpdateService	updateService;

	@Autowired
	protected StudentActivityDeleteService	deleteService;


	@PostConstruct
	void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
	}

}