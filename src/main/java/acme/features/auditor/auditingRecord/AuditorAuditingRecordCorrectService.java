package acme.features.auditor.auditingRecord;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditingRecord;
import acme.entities.audits.MarkType;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordCorrectService extends AbstractService<Auditor, AuditingRecord> {

	@Autowired
	protected AuditorAuditingRecordRepository repo;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("auditId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status = true;

		Audit audit = repo.findAudit(super.getRequest().getData("auditId", int.class));
		status = audit != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditingRecord auditingRecord = new AuditingRecord();

		auditingRecord.setCorrection(true);

		super.getBuffer().setData(auditingRecord);
	}

	@Override
	public void bind(final AuditingRecord auditingRecord) {
		assert auditingRecord != null;

		super.bind(auditingRecord, "subject", "assesment", "assesmentStartDate", "assesmentEndDate", "mark");

		Audit audit = repo.findAudit(super.getRequest().getData("auditId", int.class));
        auditingRecord.setAudit(audit);
	}

	@Override
	public void validate(final AuditingRecord auditingRecord) {
		assert auditingRecord != null;

		if (!super.getBuffer().getErrors().hasErrors("assesmentEndDate")) {
			if (auditingRecord.getAssesmentEndDate() != null) {
				super.state(auditingRecord.getAssesmentEndDate().after(auditingRecord.getAssesmentStartDate()), "assesmentEndDate", "auditor.auditingRecord.form.assesmentEndDate.notPast");
			}
		}

		if (!super.getBuffer().getErrors().hasErrors("assesmentEndDate")) {
			if (auditingRecord.getAssesmentEndDate() != null) {
				LocalDateTime firstDate = auditingRecord.getAssesmentStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				LocalDateTime lastDate = auditingRecord.getAssesmentEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				
				super.state(Duration.between(firstDate, lastDate).toHours() >= 1, "assesmentEndDate", "auditor.auditingRecord.form.assesmentEndDate.notAnHour");
			}
		}

    }

	@Override
	public void perform(final AuditingRecord auditingRecord) {
		assert auditingRecord != null;

		repo.save(auditingRecord);
	}

	@Override
	public void unbind(final AuditingRecord auditingRecord) {
		assert auditingRecord != null;

		Tuple tuple;
		
		tuple = super.unbind(auditingRecord, "subject", "assesment", "assesmentStartDate", "assesmentEndDate");

		boolean draftMode = this.repo.findAudit(super.getRequest().getData("auditId", int.class)).getDraftMode();

		tuple.put("correction", auditingRecord.isCorrection());
		tuple.put("draftMode", draftMode);

		SelectChoices choices = SelectChoices.from(MarkType.class, auditingRecord.getMark());
		tuple.put("marks", choices);
        tuple.put("mark", auditingRecord.getMark());

		tuple.put("auditId", super.getRequest().getData("auditId", int.class));

		super.getResponse().setData(tuple);
	}
}