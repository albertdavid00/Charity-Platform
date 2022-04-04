package com.example.softbinatorlabs.controllers;


import com.example.softbinatorlabs.dtos.EditEventDto;
import com.example.softbinatorlabs.dtos.EventDto;
import com.example.softbinatorlabs.services.EventService;
import com.example.softbinatorlabs.utility.KeycloakHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("")
    public ResponseEntity<?> getEvents() {
        return new ResponseEntity<>(eventService.getEvents(), HttpStatus.OK);
    }

    @PostMapping("/add/{categoryId}")
    public ResponseEntity<?> addEvent(@RequestBody EventDto eventDto, @PathVariable Long categoryId, Authentication authentication){
        eventService.addEvent(eventDto, categoryId, Long.parseLong(KeycloakHelper.getUser(authentication)));
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id, Authentication authentication){
        eventService.deleteEvent(id, Long.parseLong(KeycloakHelper.getUser(authentication)),
                KeycloakHelper.userIsAdmin(authentication));
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getEvent(@PathVariable Long id){
        return new ResponseEntity<>(eventService.getEvent(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Long id, @RequestBody EditEventDto editEventDto, Authentication authentication){
        eventService.updateEvent(id, editEventDto,
                Long.parseLong(KeycloakHelper.getUser(authentication)),
                KeycloakHelper.userIsAdmin(authentication));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}