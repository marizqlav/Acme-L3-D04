
package acme.features.assistant.sessionTutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.SessionTutorial;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantSessionTutorialListService extends AbstractService<Assistant, SessionTutorial> {

	@Autowired
	protected AssistantSessionTutorialRepository repo;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<SessionTutorial> objects;

		objects = this.repo.findSessionsOfTutorial(super.getRequest().getData("tutorialId", int.class));

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final SessionTutorial object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "description", "sessionType", "startDate", "endDate", "link");

		tuple.put("id", super.getRequest().hasData("id", int.class));
		super.getResponse().setData(tuple);

	}
}
