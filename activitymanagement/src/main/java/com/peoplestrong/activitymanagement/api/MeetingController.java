package com.peoplestrong.activitymanagement.api;

import com.peoplestrong.activitymanagement.models.Meeting;
import com.peoplestrong.activitymanagement.models.MeetingAttendee;
import com.peoplestrong.activitymanagement.payload.request.AddMeeting;
import com.peoplestrong.activitymanagement.payload.request.DeleteUserFromMeeting;
import com.peoplestrong.activitymanagement.payload.request.UserToMeeting;
import com.peoplestrong.activitymanagement.payload.request.DeleteMeetingRequest;
import com.peoplestrong.activitymanagement.payload.response.*;
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

@RestController @RequiredArgsConstructor @RequestMapping("/api") @Slf4j
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

    @Autowired
    NoAuthority noAuthority;

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
            addUserToMeeting(addMeeting.getCreator(),new UserToMeeting(userid,meeting.getId(),"Not responded"));
        }
        return ResponseEntity.created(uri).body(new IdResponse(meeting.getId()));
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
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User is already assigned to that meeting."));
        else if(status==4)
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User is not assigned to that meeting."));
        else if(status==5)
            return ResponseEntity.badRequest().body(noAuthority);
        else
            return ResponseEntity.badRequest().body(new MessageResponse("Some error occurred please try again"));

    }

    //Add a user to Meeting
    @PostMapping("/meeting/adduser")
    public ResponseEntity<?> addUserToMeeting(@RequestParam(name = "userid") Long userid,@RequestBody UserToMeeting userToMeeting)
    {
        int status=meetingService.addUserToMeeting(userid,userToMeeting);
        return returnByStatus(status);
    }

    @PutMapping("/meeting")
    public ResponseEntity<?> updateMeeting(@RequestParam(name = "userid") Long userid, @RequestBody Meeting meeting)
    {
        int status=meetingService.updateMeeting(userid,meeting);
        return returnByStatus(status);
    }

    @PutMapping("/meeting/meetingtime")
    public ResponseEntity<?> updateMeetingTime(@RequestParam(name = "userid") Long userid,@RequestBody Meeting meeting)
    {
        int status=meetingService.updateMeetingTime(userid,meeting);
        return returnByStatus(status);
    }


    @PutMapping("/meeting/description")
    public ResponseEntity<?> updateMeetingDescription(@RequestParam(name = "userid") Long userid,@RequestBody Meeting meeting)
    {
        int status=meetingService.updateMeetingDescription(userid,meeting);
        return returnByStatus(status);
    }


    @PutMapping("/meeting/purpose")
    public ResponseEntity<?> updateMeetingPurpose(@RequestParam(name = "userid") Long userid,@RequestBody Meeting meeting)
    {
        int status=meetingService.updateMeetingPurpose(userid,meeting);
        return returnByStatus(status);
    }


    @PutMapping("/meeting/place")
    public ResponseEntity<?> updateMeetingPlace(@RequestParam(name = "userid") Long userid,@RequestBody Meeting meeting)
    {
        int status=meetingService.updateMeetingPlace(userid,meeting);
        return returnByStatus(status);
    }

    @PutMapping("/meeting/status")
    public ResponseEntity<?> updateMeetingStatus(@RequestParam(name = "userid") Long userid, @RequestBody UserToMeeting userToMeeting)
    {
        int status=meetingService.updateMeetingStatus(userid,userToMeeting);
        return returnByStatus(status);
    }


    //   ----------------   DELETE Meeting ------------------------------------------------------------------------------------------------
    //Delete a meeting by id

    @DeleteMapping("/meeting")
    public ResponseEntity<?> deleteMeetingById(@RequestParam(name = "userid") Long userid, @RequestBody DeleteMeetingRequest deleteMeetingRequest)
    {
        int status=meetingService.deleteMeetingById(deleteMeetingRequest.getMeetingId(),userid);
        if(status==0)
            return ResponseEntity.ok().build();
        else if(status==1)
            return ResponseEntity.badRequest().body(meetingNotFound);
        else if(status==2)
            return ResponseEntity.badRequest().body(noAuthority);
        else
            return ResponseEntity.badRequest().body(new MessageResponse("Some error occurred please try again"));
    }

    @DeleteMapping("/meeting/user")
    public ResponseEntity<?> deleteUserFromMeeting(@RequestParam(name = "userid") Long userid, @RequestBody DeleteUserFromMeeting deleteUserFromMeeting)
    {
        int status=meetingService.deleteUserFromMeeting(deleteUserFromMeeting,userid);
        if(status==0)
            return ResponseEntity.ok().build();
        else if(status==1)
            return ResponseEntity.badRequest().body(meetingNotFound);
        else if(status==2)
            return ResponseEntity.badRequest().body(noAuthority);
        else if(status==3)
            return ResponseEntity.badRequest().body(userNotFound);
        else if(status==4)
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User already removed from the meeting."));
        else
            return ResponseEntity.badRequest().body(new MessageResponse("Some error occurred please try again"));

    }

    //   ----------------   READ Meeting ------------------------------------------------------------------------------------------------
    @GetMapping("/meeting/creator")
    public ResponseEntity<?> findMeetingByCreatorid(@RequestParam(name = "userid") Long userid)
    {
        return meetingService.findMeetingByCreatorid(userid);
    }

    @GetMapping("/meeting/noninvited")
    public ResponseEntity<?> findAllNonInvitedUsers(@RequestParam(name = "userid") Long userId,@RequestParam Long meetingId)
    {
        return meetingService.findAllNonInvitedUsers(userId,meetingId);
    }
    @GetMapping("/meeting/user")
    public ResponseEntity<?> getAllMeetingsByUserid(@RequestParam(name = "userid") Long userid)
    {
        return meetingService.getAllMeetingsByUserid(userid);
    }



}
