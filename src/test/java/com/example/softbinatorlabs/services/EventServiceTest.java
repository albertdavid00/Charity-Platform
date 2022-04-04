package com.example.softbinatorlabs.services;

import com.example.softbinatorlabs.dtos.EventDto;
import com.example.softbinatorlabs.models.Category;
import com.example.softbinatorlabs.models.Event;
import com.example.softbinatorlabs.models.User;
import com.example.softbinatorlabs.repositories.CategoryRepository;
import com.example.softbinatorlabs.repositories.EventRepository;
import com.example.softbinatorlabs.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;
@DataJpaTest
public class EventServiceTest {
    @Mock
    private EventRepository eventRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private EventService eventService;

    @Test
    void getEvents(){
        Event event1 = Event.builder()
                .title("title1")
                .description("description")
                .currentAmount(0.0)
                .targetAmount(0.0)
                .build();
        Event event2 = Event.builder()
                .title("title2")
                .description("description")
                .currentAmount(0.0)
                .targetAmount(0.0)
                .build();
        List<Event> eventList = List.of(event1, event2);

        when(eventRepository.findAll()).thenReturn(eventList);

        List<Event> events = eventService.getEvents();
        Assertions.assertEquals(eventList, events);
        verify(eventRepository).findAll();
    }

    @Test
    public void addEventTest(){
        User user = User.builder().username("user").email("email").build();
        Category category = Category.builder().name("categ").build();
        Event event = Event.builder()
                .title("title")
                .description("description")
                .currentAmount(0.0)
                .targetAmount(0.0)
                .user(user)
                .category(category)
                .build();
        EventDto eventDto = EventDto.builder().title("title").description("description").currentAmount(0.0).targetAmount(0.0).build();
        when(eventRepository.save(event)).thenReturn(event);
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(user));
        when(categoryRepository.existsById(1L)).thenReturn(true);
        when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(category));

        assertEquals(event.getTitle(), eventService.addEvent(eventDto, 1L, 1L).getTitle());
    }
}
