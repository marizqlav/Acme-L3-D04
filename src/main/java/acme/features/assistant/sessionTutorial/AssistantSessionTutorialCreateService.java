
package acme.features.assistant.sessionTutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.lectures.LectureType;
import acme.entities.tutorial.SessionTutorial;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantSessionTutorialCreateService extends AbstractService<Assistant, SessionTutorial> {

	@Autowired
	protected AssistantSessionTutorialRepository repo;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final SessionTutorial object = new SessionTutorial();

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final SessionTutorial object) {

		assert object != null;
		super.bind(object, "title", "description", "sessionType", "startDate", "endDate", "link");
	}

	@Override
	public void unbind(final SessionTutorial object) {
		assert object != null;

		Tuple tuple;
		SelectChoices choices;

		choices = SelectChoices.from(LectureType.class, object.getSessionType());
		tuple = super.unbind(object, "title", "description", "sessionType", "startDate", "endDate", "link");
		tuple.put("sessionTypes", choices);
		super.getResponse().setData(tuple);
	}

	@Override
	public void validate(final SessionTutorial object) {
		if (!super.getBuffer().getErrors().hasErrors("sessionType"))
			super.state(!object.getSessionType().equals(LectureType.BALANCED), "sessionType", "assistant.sessionTutorial.form.error.sessionType");
	}

	@Override
	public void perform(final SessionTutorial object) {
		assert object != null;
		//object.setDraftmode(true);

		this.repo.save(object);
	}
}