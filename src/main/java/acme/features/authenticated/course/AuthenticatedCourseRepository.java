/*
 * AuthenticatedAnnouncementRepository.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.lectures.Lecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface AuthenticatedCourseRepository extends AbstractRepository {

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select c from Course c where c.draftMode = 0")
	Collection<Course> findCourses();

	@Query("select l from Lecturer l")
	Collection<Lecturer> findAllLecturers();

	@Query("select c.lecturer from Course c where c.id = :id")
	Lecturer findLecturersByCourseId(int id);

	@Query("select c.lecture from CourseLecture c where c.course.id = :id")
	Collection<Lecture> findLecturesByCourseId(int id);

}
