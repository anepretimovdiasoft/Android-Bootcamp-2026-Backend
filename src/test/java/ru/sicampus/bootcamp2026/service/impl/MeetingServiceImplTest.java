//package ru.sicampus.bootcamp2026.service.impl;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import ru.sicampus.bootcamp2026.dto.CreateMeetingRequestDTO;
//import ru.sicampus.bootcamp2026.dto.MeetingDTO;
//import ru.sicampus.bootcamp2026.entity.Meeting;
//import ru.sicampus.bootcamp2026.entity.Person;
//import ru.sicampus.bootcamp2026.repository.AuthorityRepository;
//import ru.sicampus.bootcamp2026.repository.DepartmentRepository;
//import ru.sicampus.bootcamp2026.repository.MeetingRepository;
//import ru.sicampus.bootcamp2026.repository.PersonRepository;
//import ru.sicampus.bootcamp2026.util.PersonMapper;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class MeetingServiceImplTest {
//
//    @Mock
//    private PersonRepository personRepository;
//
//    @Mock
//    private DepartmentRepository departmentRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @Mock
//    private AuthorityRepository authorityRepository;
//
//    @Mock
//    private PersonMapper personMapper;
//
//    @Mock
//    private MeetingRepository meetingRepository;
//
//    @InjectMocks
//    private PersonServiceImpl personService;
//
//    @InjectMocks
//    private MeetingServiceImpl meetingService;
//
//    @Test
//    void createMeeting() {
//        // Given
//        CreateMeetingRequestDTO request = new CreateMeetingRequestDTO();
//        request.setTitle("Совещание");
//        request.setDescription("Обсуждение проекта");
//        request.setStartTime(LocalDateTime.of(2025, 1, 1, 10, 0));
//        request.setEndTime(LocalDateTime.of(2025, 1, 1, 11, 0));
//        request.setOrganizerId(1L);
//
//        Person organizer = new Person();
//        organizer.setId(1L);
//        organizer.setUsername("ivan");
//
//        Meeting meeting = new Meeting();
//        meeting.setTitle("Совещание");
//        meeting.setOrganizer(organizer);
//        meeting.setStartTime(request.getStartTime());
//        meeting.setEndTime(request.getEndTime());
//
//        // Mock
//        when(personRepository.findById(1L))
//                .thenReturn(Optional.of(organizer));
//
//        when(meetingRepository.save(any(Meeting.class)))
//                .thenReturn(meeting);
//
//        // When
//        MeetingDTO result = meetingService.createMeeting(request);
//
//        // Then
//        assertNotNull(result);
//        assertEquals("Совещание", result.getTitle());
//        assertEquals(1L, result.getOrganizerId());
//
//        verify(personRepository, times(1)).findById(1L);
//        verify(meetingRepository, times(1)).save(any(Meeting.class));
//    }
//}
