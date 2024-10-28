package com.turf.playarena.controller;

import com.turf.playarena.dto.LoginDto;
import com.turf.playarena.dto.SignUpDto;
import com.turf.playarena.dto.TurfBookingDto;
import com.turf.playarena.dto.TurfCreationDto;
import com.turf.playarena.response.LoginResponse;
import com.turf.playarena.service.TurfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/turf")
public class TurfController {

    @Autowired
    private TurfService turfService;

    /*
    Below APIs belong to the user level operations.
     */
    @PostMapping("/signup")
    public ResponseEntity<Object> userDetails(@RequestBody SignUpDto signUpDto){
        return turfService.userCreated(signUpDto);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginDto loginDto){
        return turfService.login(loginDto);
    }

    @GetMapping("/userDetails")
    public ResponseEntity<Object> getUserDetails(){
        return turfService.getUserDetails();
    }


    /*
    Below APIs belong to the admin levelstyle={"width: 100px"} operations.
     */
    @PostMapping("/adminCreation")
    public ResponseEntity<Object> adminCreation(@RequestBody SignUpDto signUpDto){
        return  turfService.adminCreation(signUpDto);
    }
    @PostMapping("/adminLogin")
    public ResponseEntity<Object> adminLogin(@RequestBody LoginDto loginDto){
        return  turfService.adminLogin(loginDto);
    }
    @GetMapping("/userDetails/{id}")
    public ResponseEntity<Object> getUserDetail(@PathVariable int id){
        return turfService.getUserDetail(id);
    }

    @DeleteMapping("/deleteDetails/{id}")
    public ResponseEntity<Object> deleteUserDetail(@PathVariable int id){
        return turfService.deleteUserDetail(id);
    }


    @DeleteMapping("/deleteTurf/{id}")
    public ResponseEntity<Object> deleteTurf(@PathVariable int id){
        return turfService.deleteTurf(id);
    }



    /*
    Turf related APIs
     */
    @PostMapping("/turfCreation")
    public ResponseEntity<Object> turfCreation(@RequestBody TurfCreationDto turfCreationDto){
        return  turfService.turfCreation(turfCreationDto);
    }
    @GetMapping("/getTurfDetails")
    public  ResponseEntity<Object> getTurfDetails(){
        return turfService.getTurfDetails();
    }

    @GetMapping("/getTurfDetailsById/{id}")
    public ResponseEntity<Object> getTurfDetailsById(@PathVariable int id){
        return turfService.getTurfDetailsById(id);
    }

    @GetMapping("/getTurfDetails/{location}")
    public ResponseEntity<Object> getTurfDetailsByLocation(@PathVariable String location){
        return turfService.getTurfDetailsByLocation(location);
    }

    @PostMapping("/bookTurf")
    public ResponseEntity<Object> bookTurf(@RequestBody TurfBookingDto turfBookingDto){
        return turfService.bookTurf(turfBookingDto);
    }

    @DeleteMapping("/cancelBooking/{bid}")
    public ResponseEntity<Object> cancelBooking(@PathVariable int bid){
        return turfService.cancelBooking(bid);
    }

    @GetMapping("/retrieveByName/{name}")
    public ResponseEntity<Object> retriveByName(@PathVariable String name){
        return  turfService.retrieveByName(name);

    }
}
