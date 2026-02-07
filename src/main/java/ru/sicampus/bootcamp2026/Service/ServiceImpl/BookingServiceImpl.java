package ru.sicampus.bootcamp2026.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.Dto.response.Booking.BookingByDayResponse;
import ru.sicampus.bootcamp2026.Dto.response.Booking.BookingByMonthResponse;
import ru.sicampus.bootcamp2026.Dto.response.Booking.BookingByWeekResponse;
import ru.sicampus.bootcamp2026.Dto.response.Booking.BookingUpdateResponse;
import ru.sicampus.bootcamp2026.Entity.Booking;
import ru.sicampus.bootcamp2026.Entity.Employee;
import ru.sicampus.bootcamp2026.Excepations.EmployeeNotFound;
import ru.sicampus.bootcamp2026.Repository.BookingRepository;
import ru.sicampus.bootcamp2026.Repository.EmployeeRepository;
import ru.sicampus.bootcamp2026.Service.BookingService;
import ru.sicampus.bootcamp2026.Service.TokenAuthService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private TokenAuthService tokenAuthService;
    @Autowired
    private EmployeeRepository employeeRepository;
    public BookingByDayResponse getBookingByDay() {
        String token = SecurityContextHolder.getContext().getAuthentication().getName();
        Employee employee=employeeRepository.findByMail(token).orElseThrow();
        List<Booking> bookings = bookingRepository.findByEmployee(employee);
        LocalDate date=LocalDate.now();
        List<Booking> bookingList = bookings.stream().filter(b -> b.getStart().toLocalDate() == date).toList();
        BookingByDayResponse bookingByDayResponse = new BookingByDayResponse();
        bookingByDayResponse.setBookings(bookingList);
        return bookingByDayResponse;
    }
    public BookingByMonthResponse getBookingByMonth(){
        String token=SecurityContextHolder.getContext().getAuthentication().getName();
        Employee employee=employeeRepository.findByMail(token).orElseThrow();
        List<Booking> bookings=bookingRepository.findByEmployee(employee);
        LocalDate date=LocalDate.now();
        List<Map<String,Object>> bookingList= new ArrayList<>((Collection) bookings.stream().filter(
                b->b.getStart().toLocalDate().getMonth()==date.getMonth())
                .collect(Collectors.toMap(b->b.getStart().toLocalDate(),b->{
                    Map<String,Object> book=new LinkedHashMap<>();
                    book.put("name",b.getName());
                    book.put("admin",b.getEmployee());
                    book.put("start",b.getStart().toLocalTime());
                    book.put("end",b.getEnd().toLocalTime());
                    return book;
                })));
        BookingByMonthResponse bookingByMonthResponse=new BookingByMonthResponse();
        bookingByMonthResponse.setBookings(bookingList);
        return bookingByMonthResponse;
    }
    public BookingByWeekResponse getBookingByWeek() {
        String token = SecurityContextHolder.getContext().getAuthentication().getName();
        Employee employee = employeeRepository.findByMail(token).orElseThrow();
        List<Booking> bookings = bookingRepository.findByEmployee(employee);
        LocalDate date = LocalDate.now();
        LocalDate dateFinal = date.plusDays(7);
        List<List<Map<String, Object>>> booking = new ArrayList<>();
        for (int i = 0; i < dateFinal.getDayOfMonth(); i++) {
            LocalDate time = date.plusDays(i);
            List<Booking> bookingList = bookingRepository.findByStart(time);
            List<Map<String, Object>> booking1 = new ArrayList<>((Collection) bookingList.stream().collect(Collectors.toMap(
                    b -> b.getStart().toLocalDate(), b -> {
                        Map<String, Object> book = new LinkedHashMap<>();
                        book.put("name", b.getName());
                        book.put("admin", b.getEmployee());
                        book.put("start", b.getStart().toLocalTime());
                        book.put("end", b.getEnd().toLocalTime());
                        return book;
                    })));
            booking.add(booking1);
        }
        BookingByWeekResponse bookingByWeekResponse = new BookingByWeekResponse();
        bookingByWeekResponse.setBookings(booking);
        return bookingByWeekResponse;
    }

}
