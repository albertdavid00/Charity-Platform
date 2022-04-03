package com.example.softbinatorlabs.services;

import com.example.softbinatorlabs.models.Event;
import com.example.softbinatorlabs.repositories.CategoryRepository;
import com.example.softbinatorlabs.repositories.EventRepository;
import com.example.softbinatorlabs.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    public EventService(UserRepository userRepository, CategoryRepository categoryRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.eventRepository = eventRepository;
    }


    public List<Event> getEvents() {
        return eventRepository.findAll();
    }
}
