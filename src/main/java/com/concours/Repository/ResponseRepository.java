package com.concours.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.concours.Model.Response;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {

}
