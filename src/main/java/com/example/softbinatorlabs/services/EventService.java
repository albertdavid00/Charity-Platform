package com.example.softbinatorlabs.services;

import com.example.softbinatorlabs.dtos.EditEventDto;
import com.example.softbinatorlabs.dtos.EventDto;
import com.example.softbinatorlabs.models.Category;
import com.example.softbinatorlabs.models.Event;
import com.example.softbinatorlabs.models.User;
import com.example.softbinatorlabs.repositories.CategoryRepository;
import com.example.softbinatorlabs.repositories.EventRepository;
import com.example.softbinatorlabs.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
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

    public Event addEvent(EventDto eventDto, Long categoryId, Long userId) {
        User user = userRepository.findById(userId).get();

        if (categoryRepository.existsById(categoryId)){
            Category category = categoryRepository.findById(categoryId).get();
            Event event = Event.builder()
                    .title(eventDto.getTitle())
                    .description(eventDto.getDescription())
                    .currentAmount(eventDto.getCurrentAmount())
                    .targetAmount(eventDto.getTargetAmount())
                    .user(user)
                    .category(category)
                    .build();
            return eventRepository.save(event);
        }
        else throw new BadRequestException("Category with id " + categoryId + "doesn't exist");

    }

    public void deleteEvent(Long id, Long userId, Boolean isAdmin)  {

        if (eventRepository.existsById(id)){
            Event event = eventRepository.getById(id);
            if (event.getUser().getId().equals(userId) || isAdmin){
                eventRepository.deleteById(id);
            }
        }
        else{
            throw new BadRequestException("Event with id " + id + " doesn't exist");
        }
    }

    public Event getEvent(Long id) {
        if (eventRepository.existsById(id)) {
            return eventRepository.findById(id).get();
        }
        else throw new BadRequestException("Event with id " + id + " doesn't exist");
    }

    public void updateEvent(Long eventId, EditEventDto eventDto, Long userId, Boolean userIsAdmin) {
        Event event = eventRepository.findById(eventId).get();
        if (userId.equals(event.getUser().getId()) || userIsAdmin){
            event.setTitle(eventDto.getTitle());
            event.setDescription(eventDto.getDescription());
            event.setTargetAmount(eventDto.getTargetAmount());
            eventRepository.save(event);
        }

    }
}
