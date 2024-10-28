package com.turf.playarena.service;

import com.turf.playarena.dto.*;
import com.turf.playarena.jwt.JwtUtil;
import com.turf.playarena.pojo.*;
import com.turf.playarena.repository.BookingRepository;
import com.turf.playarena.repository.TurfRepository;
import com.turf.playarena.repository.UserRepository;
import com.turf.playarena.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TurfService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TurfRepository turfRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;


    public ResponseEntity<Object> userCreated(SignUpDto signUpDto) {
        User userDetails = new User();

        List<User> allDetails = userRepository.findAll();

        System.out.println("details --------------------->" + allDetails);
        if (allDetails == null) {
            return new ResponseEntity<Object>("No User Present in the Database!", HttpStatus.ALREADY_REPORTED);
        }
        User details = userRepository.findByEmail(signUpDto.getEmail());
        System.out.println("details --------------------->" + allDetails);

        if (details != null) {
            return new ResponseEntity<Object>("User elready exist with the same email in the Database!", HttpStatus.ALREADY_REPORTED);
        }
        userDetails.setName(signUpDto.getName());
        userDetails.setEmail(signUpDto.getEmail());
        userDetails.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        userDetails.setPhoneNumber(signUpDto.getPhoneNumber());
        userDetails.setRoles(signUpDto.getRoles());
        userRepository.save(userDetails);
        return new ResponseEntity<Object>("User Created!", HttpStatus.CREATED);
    }

    public LoginResponse login(LoginDto loginDto) {

        LoginResponse loginResponse = new LoginResponse();
        User detail = userRepository.findByEmail(loginDto.getEmail());
        System.out.println("details =================>" + detail);
        String password = loginDto.getPassword();
        System.out.println("details =================>" + password);
        if (detail != null) {
            if (passwordEncoder.matches(loginDto.getPassword(), detail.getPassword())) {
                loginResponse.setMessage("Login Successful!!");
                loginResponse.setToken(jwtUtil.generateToken(loginDto.getEmail()));
                return loginResponse;
            }
        }
        loginResponse.setMessage("User not exist!!!");
        return loginResponse;

    }

    public ResponseEntity<Object> getUserDetails() {
        List<User> userDetails = userRepository.findAll();
        if (userDetails == null) {
            return new ResponseEntity<Object>("No User Present in the Database!", HttpStatus.ALREADY_REPORTED);
        }
        return new ResponseEntity<Object>(userDetails, HttpStatus.OK);
    }

    public ResponseEntity<Object> getUserDetail(int id) {
        Optional details = userRepository.findById(id);
        if (details.isEmpty()) {
            return new ResponseEntity<Object>("No User Present with this id in the Database!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Object>(details, HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteUserDetail(int id) {
        Optional details = userRepository.findById(id);
        if (details.isEmpty()) {
            return new ResponseEntity<Object>("No User Present with this id in the Database to delete!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        userRepository.deleteById(id);
        return new ResponseEntity<Object>("Deleted Successfully!", HttpStatus.OK);

    }

    /*
    admin level operations
     */
    public ResponseEntity<Object> adminCreation(SignUpDto signUpDto) {
        User userDetails = new User();

        List<User> allDetails = userRepository.findAll();

        System.out.println("details --------------------->" + allDetails);
        if (allDetails == null) {
            return new ResponseEntity<Object>("No User Present in the Database!", HttpStatus.ALREADY_REPORTED);
        }
        User details = userRepository.findByEmail(signUpDto.getEmail());
        System.out.println("details --------------------->" + allDetails);

        if (details != null) {
            return new ResponseEntity<Object>("Admin elready exist with the same email in the Database!", HttpStatus.ALREADY_REPORTED);
        }
        userDetails.setName(signUpDto.getName());
        userDetails.setEmail(signUpDto.getEmail());
        userDetails.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        userDetails.setPhoneNumber(signUpDto.getPhoneNumber());
        userDetails.setRoles(signUpDto.getRoles());
        userRepository.save(userDetails);
        return new ResponseEntity<Object>("Admin Created!", HttpStatus.CREATED);
    }

    public ResponseEntity<Object> adminLogin(LoginDto loginDto) {
        User detail = userRepository.findByEmail(loginDto.getEmail());
        System.out.println("details =================>" + detail);
        String password = loginDto.getPassword();
        System.out.println("password =================>" + password);
        if (detail != null) {
            if (passwordEncoder.matches(loginDto.getPassword(), detail.getPassword())) {
                return ResponseEntity.ok(jwtUtil.generateToken(loginDto.getEmail()));
            }
        }
        return new ResponseEntity<Object>("Admin doesn't Exist !", HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<Object> turfCreation(TurfCreationDto turfCreationDto) {
        if(turfCreationDto.getTurfName() == null || turfCreationDto.getSqft() == null
                || turfCreationDto.getLocation() == null || turfCreationDto.getPhoneNumber() == null ||
                turfCreationDto.getTurfName().isEmpty() || turfCreationDto.getLocation().isEmpty() ||
                turfCreationDto.getSqft().isEmpty() || turfCreationDto.getSqft().isEmpty() ||
                turfCreationDto.getPricePerHour()<0){
            return new ResponseEntity<>("Please enter valid field values !!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        TurfDetails turfDetails = new TurfDetails();
        turfDetails.setTurfName(turfCreationDto.getTurfName());
        turfDetails.setSqft(turfCreationDto.getSqft());
        turfDetails.setLocation(turfCreationDto.getLocation());
        turfDetails.setPhoneNumber(turfCreationDto.getPhoneNumber());
        turfDetails.setPricePerHour(turfCreationDto.getPricePerHour());
        turfRepository.save(turfDetails);
        return new ResponseEntity<>("Created !!!", HttpStatus.CREATED);
    }

    public ResponseEntity<Object> getTurfDetails() {
        List<TurfDetails> details = turfRepository.findAll();
        if(details.isEmpty()){
            return new ResponseEntity<>("No Turfs are present!!!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(details, HttpStatus.OK);
    }

    public ResponseEntity<Object> getTurfDetailsById(int id) {
        // Fetch all available slots
        List<String> allSlotDetail = new ArrayList<>(Arrays.asList(TimeSlot.SLOT1.name(), TimeSlot.SLOT2.name(),
                TimeSlot.SLOT3.name(),TimeSlot.SLOT4.name(),TimeSlot.SLOT5.name(),TimeSlot.SLOT6.name(),
                TimeSlot.SLOT7.name(),TimeSlot.SLOT8.name(),TimeSlot.SLOT9.name(),TimeSlot.SLOT10.name(),
                TimeSlot.SLOT11.name(),TimeSlot.SLOT12.name(),TimeSlot.SLOT13.name(),TimeSlot.SLOT14.name(),
                TimeSlot.SLOT15.name(),TimeSlot.SLOT16.name(),TimeSlot.SLOT17.name()));
        TurfDetails turfDetails = turfRepository.findById(id).orElse(null);

        if (turfDetails == null) {
            return new ResponseEntity<>("Turf not found", HttpStatus.NOT_FOUND);
        }

        List<BookingDetails> bookingDetails = bookingRepository.findByTurfDetailsId(id);

        // Remove booked slots from available slots
        for (BookingDetails booking : bookingDetails) {
            allSlotDetail.remove(booking.getTimeslot().name());
        }

        BookingDto bookingDto = new BookingDto();
        bookingDto.setTurfName(turfDetails.getTurfName());
        bookingDto.setPhoneNumber(turfDetails.getPhoneNumber());
        bookingDto.setSqft(turfDetails.getSqft());
        bookingDto.setTid(turfDetails.getId());
        bookingDto.setLocation(turfDetails.getLocation());
        bookingDto.setAvailableSlots(allSlotDetail);
        bookingDto.setPricePerHour(turfDetails.getPricePerHour());

        return new ResponseEntity<>(bookingDto, HttpStatus.OK);
    }

    public ResponseEntity<Object> getTurfDetailsByLocation(String location) {
        List<TurfDetails> turfDetails = turfRepository.findByLocation(location);
        System.out.println("turfDetails ------------>"+turfDetails);
        if (turfDetails.isEmpty()) {
            return new ResponseEntity<>("No Turf present in the location", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(turfDetails, HttpStatus.OK);
    }


    public ResponseEntity<Object> bookTurf(TurfBookingDto turfBookingDto) {
        BookingDetails details = bookingRepository.getDetails(turfBookingDto.getTurfDetailsId(),turfBookingDto.getTimeslot().name());
        if(details!=null){
            return new ResponseEntity<>("Already Booked !!!", HttpStatus.CREATED);
        }
        BookingDetails bookingDetails = new BookingDetails();
        bookingDetails.setTurfDetailsId(turfBookingDto.getTurfDetailsId());
        bookingDetails.setTimeslot(turfBookingDto.getTimeslot());
        bookingRepository.save(bookingDetails);
        return new ResponseEntity<>("Booked Successfully!", HttpStatus.CREATED);
    }

    public ResponseEntity<Object> cancelBooking(int bid) {
        BookingDetails bookingDetails = bookingRepository.findById(bid).orElse(null);
        System.out.println("bookinDetails --------------->"+bookingDetails);
        if (bookingDetails == null) {
            return new ResponseEntity<>("No Booking to cancel", HttpStatus.NOT_FOUND);
        }

        TimeSlot canceledSlot = bookingDetails.getTimeslot();
        int turfDetailsId = bookingDetails.getTurfDetailsId();

        // Delete the booking
        bookingRepository.deleteById(bid);

        // Fetch turf details
        TurfDetails turfDetails = turfRepository.findById(turfDetailsId).orElse(null);
        if (turfDetails != null) {
            // Fetch available slots
            List<String> availableSlots = new ArrayList<>(Arrays.asList(TimeSlot.SLOT1.getDisplayName(), TimeSlot.SLOT2.getDisplayName()));

            // Remove booked slot from available slots
            availableSlots.remove(canceledSlot.getDisplayName());
        }

        return new ResponseEntity<>("Cancelled!!!!!", HttpStatus.OK);
    }


    public ResponseEntity<Object> deleteTurf(int id) {
        TurfDetails turfDetails = turfRepository.findById(id).orElse(null);
        System.out.println("turfDetails ------------->"+turfDetails);
        if(turfDetails == null){
            return new ResponseEntity<>("No turf Present with this name or id!!!!", HttpStatus.OK);
        }
        turfRepository.deleteById(id);
        return new ResponseEntity<>("Deleted!!!!", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> retrieveByName(String name) {
        User user = userRepository.findByName(name);
        if(user == null){
            return new ResponseEntity<>("No user Present with this name !!!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}

