package com.unity.messagingservice.repository;

import com.unity.messagingservice.persistence.entity.UnityMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<UnityMessage, String>
{}
