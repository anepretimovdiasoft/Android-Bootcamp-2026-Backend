package ru.examle.edu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import ru.examle.edu.dto.*;
import ru.examle.edu.entity.*;
import ru.examle.edu.entity.Department;
import ru.examle.edu.entity.Invitation;
import ru.examle.edu.entity.Meeting;
import ru.examle.edu.entity.Person;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EntityMapper {

    EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);

    // Department mappings
    DepartmentDto toDepartmentDto(Department department);
    DepartmentSimpleDto toDepartmentSimpleDto(Department department);
    Department toDepartment(DepartmentRequest request);
    void updateDepartment(@MappingTarget Department department, DepartmentRequest request);

    // Person mappings
    @Mapping(target = "id", ignore = true)
    PersonDto toPersonDto(Person person);
    PersonSimpleDto toPersonSimpleDto(Person person);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "organizedMeetings", ignore = true)
    @Mapping(target = "invitations", ignore = true)
    Person toPerson(PersonRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "organizedMeetings", ignore = true)
    @Mapping(target = "invitations", ignore = true)
    void updatePerson(@MappingTarget Person person, PersonRequest request);

    // Meeting mappings
    MeetingDto toMeetingDto(Meeting meeting);
    MeetingSimpleDto toMeetingSimpleDto(Meeting meeting);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organizer", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "invitations", ignore = true)
    Meeting toMeeting(MeetingRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organizer", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "invitations", ignore = true)
    void updateMeeting(@MappingTarget Meeting meeting, MeetingRequest request);

    // Invitation mappings
    InvitationDto toInvitationDto(Invitation invitation);
    InvitationSimpleDto toInvitationSimpleDto(Invitation invitation);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "meeting", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "responseDate", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Invitation toInvitation(InvitationRequest request);

    // List mappings
    List<DepartmentDto> toDepartmentDtoList(List<Department> departments);
    List<PersonDto> toPersonDtoList(List<Person> persons);
    List<MeetingDto> toMeetingDtoList(List<Meeting> meetings);
    List<InvitationDto> toInvitationDtoList(List<Invitation> invitations);
}