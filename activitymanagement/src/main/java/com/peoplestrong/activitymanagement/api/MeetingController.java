package com.peoplestrong.activitymanagement.api;

import com.peoplestrong.activitymanagement.models.Meeting;
import com.peoplestrong.activitymanagement.models.MeetingAttendee;
import com.peoplestrong.activitymanagement.payload.request.AddMeeting;
import com.peoplestrong.activitymanagement.payload.request.DeleteUserFromMeeting;
import com.peoplestrong.activitymanagement.payload.request.UserToMeeting;
import com.peoplestrong.activitymanagement.payload.request.DeleteMeetingRequest;
import com.peoplestrong.activitymanagement.payload.response.MeetingNotFound;
import com.peoplestrong.activitymanagement.payload.response.MessageResponse;
import com.peoplestrong.activitymanagement.payload.response.UserNotFound;
import com.peoplestrong.activitymanagement.repo.MeetingRepo;
import com.peoplestrong.activitymanagement.service.MeetingService;
import com.peoplestrong.activitymanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@RestController @RequiredArgsConstructor @RequestMapping("/api") @Slf4j @PreAuthorize("hasRole('ROLE_USER')")
public class MeetingController {
    @Autowired
    MeetingService meetingService;

    @Autowired
    MeetingRepo meetingRepo;

    @Autowired
    UserService userService;

    @Autowired
    UserNotFound userNotFound;

    @Autowired
    MeetingNotFound meetingNotFound;

//    --------------   CREATE Meeting   ------------------------------------------------------------------------------------------------
    //Add a meeting
    @PostMapping("/meeting")
    public ResponseEntity<?> addMeeting(@RequestBody AddMeeting addMeeting)
    {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Meeting meeting=new Meeting(null,
                    addMeeting.getPurpose(),
                addMeeting.getCreator(),
                addMeeting.getPlace(),
                LocalDateTime.parse(addMeeting.getCreationTime(), formatter),
                LocalDateTime.parse(addMeeting.getMeetingTime(), formatter),
                addMeeting.getDescription()
                );
        meetingRepo.save(meeting);
        URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/meeting").toUriString());
        for(Long userid:addMeeting.getMeetingAttendees())
        {
            addUserToMeeting(new UserToMeeting(userid,meeting.getId(),"Not responded"));
        }
        return ResponseEntity.created(uri).body(meeting.getId());
    }

//   ----------------   UPDATE Meeting ------------------------------------------------------------------------------------------------
    private ResponseEntity<?> returnByStatus(int status)
    {
        if(status==0)
            return ResponseEntity.ok().build();
        else if(status==1)
            return ResponseEntity.badRequest().body(meetingNotFound);
        else if(status==2)
            return ResponseEntity.badRequest().body(userNotFound);
        else if(status==3)
            return ResponseEntity.badRequest().body("Error: User is already assigned to that meeting.");
        else if(status==4)
            return ResponseEntity.badRequest().body("Error: User is not assigned to that meeting.");
        else
            return ResponseEntity.badRequest().body("Some error occurred please try again");

    }

    //Add a user to Meeting
    @PostMapping("/meeting/adduser")
    public ResponseEntity<?> addUserToMeeting(@RequestBody UserToMeeting userToMeeting)
    {
        int status=meetingService.addUserToMeeting(userToMeeting);
        return returnByStatus(status);
    }

    @PutMapping("/meeting/{userid}")
    public ResponseEntity<?> updateMeeting(@PathVariable(name = "userid") Long userid, @RequestBody Meeting meeting)
    {
        if(!Objects.equals(userid, meeting.getCreator()))
            return ResponseEntity.badRequest().body("Error: Only creator of the Meeting can change thr creator property.");
        int status=meetingService.updateMeeting(meeting);
        return returnByStatus(status);
    }

    @PutMapping("/meeting/meetingtime/{userid}")
    public ResponseEntity<?> updateMeetingTime(@PathVariable(name = "userid") Long userid,@RequestBody Meeting meeting)
    {
        if(!Objects.equals(userid, meeting.getCreator()))
            return ResponseEntity.badRequest().body("Error: Only creator of the Meeting can change thr creator property.");
        int status=meetingService.updateMeetingTime(meeting);
        return returnByStatus(status);
    }


    @PutMapping("/meeting/description/{userid}")
    public ResponseEntity<?> updateMeetingDescription(@PathVariable(name = "userid") Long userid,@RequestBody Meeting meeting)
    {
        if(!Objects.equals(userid, meeting.getCreator()))
            return ResponseEntity.badRequest().body("Error: Only creator of the meeting can change the creator property.");
        int status=meetingService.updateMeetingDescription(meeting);
        return returnByStatus(status);
    }


    @PutMapping("/meeting/purpose/{userid}")
    public ResponseEntity<?> updateMeetingPurpose(@PathVariable(name = "userid") Long userid,@RequestBody Meeting meeting)
    {
        if(!Objects.equals(userid, meeting.getCreator()))
            return ResponseEntity.badRequest().body("Error: Only creator of the Meeting can change thr creator property.");
        int status=meetingService.updateMeetingPurpose(meeting);
        return returnByStatus(status);
    }


    @PutMapping("/meeting/place/{userid}")
    public ResponseEntity<?> updateMeetingPlace(@PathVariable(name = "userid") Long userid,@RequestBody Meeting meeting)
    {
        if(!Objects.equals(userid, meeting.getCreator()))
            return ResponseEntity.badRequest().body("Error: Only creator of the Meeting can change thr creator property.");
        int status=meetingService.updateMeetingPlace(meeting);
        return returnByStatus(status);
    }

    @PutMapping("/meeting/status/{userid}")
    public ResponseEntity<?> updateMeetingStatus(@PathVariable(name = "userid") Long userid, @RequestBody UserToMeeting userToMeeting)
    {
        int status=meetingService.updateMeetingStatus(userid,userToMeeting);
        return returnByStatus(status);
    }


    //   ----------------   DELETE Meeting ------------------------------------------------------------------------------------------------
    //Delete a meeting by id

    @DeleteMapping("/meeting/{userid}")
    public ResponseEntity<?> deleteMeetingById(@PathVariable(name = "userid") Long userid, @RequestBody DeleteMeetingRequest deleteMeetingRequest)
    {
        int status=meetingService.deleteMeetingById(deleteMeetingRequest.getMeetingId(),userid);
        if(status==0)
            return ResponseEntity.ok().build();
        else if(status==1)
            return ResponseEntity.badRequest().body(meetingNotFound);
        else if(status==2)
            return ResponseEntity.badRequest().body("Error: Only meeting creator can delete the meeting");
        else
            return ResponseEntity.badRequest().body("Some error occurred please try again");
    }

    @DeleteMapping("/meeting/user/{userid}")
    public ResponseEntity<?> deleteUserFromMeeting(@PathVariable(name = "userid") Long userid, @RequestBody DeleteUserFromMeeting deleteUserFromMeeting)
    {
        int status=meetingService.deleteUserFromMeeting(deleteUserFromMeeting,userid);
        if(status==0)
            return ResponseEntity.ok().build();
        else if(status==1)
            return ResponseEntity.badRequest().body(meetingNotFound);
        else if(status==2)
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Only creator can delete the meeting"));
        else if(status==3)
            return ResponseEntity.badRequest().body(userNotFound);
        else if(status==4)
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User already removed from the meeting."));
        else
            return ResponseEntity.badRequest().body(new MessageResponse("Some error occurred please try again"));

    }

    //   ----------------   READ Meeting ------------------------------------------------------------------------------------------------
    @GetMapping("/meeting/creator/{userid}")
    public ResponseEntity<?> findMeetingByCreatorid(@PathVariable(name = "userid") Long userid)
    {
        return meetingService.findMeetingByCreatorid(userid);
    }

    @GetMapping("/meeting/user/{userid}")
    public ResponseEntity<?> getAllMeetingsByUserid(@PathVariable(name = "userid") Long userid)
    {
        return meetingService.getAllMeetingsByUserid(userid);
    }



}
