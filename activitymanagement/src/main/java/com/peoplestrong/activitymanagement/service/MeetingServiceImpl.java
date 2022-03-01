package com.peoplestrong.activitymanagement.service;

import com.peoplestrong.activitymanagement.models.*;
import com.peoplestrong.activitymanagement.payload.request.DeleteUserFromMeeting;
import com.peoplestrong.activitymanagement.payload.request.UserToMeeting;
import com.peoplestrong.activitymanagement.payload.response.*;
import com.peoplestrong.activitymanagement.repo.MeetingAttendeeRepo;
import com.peoplestrong.activitymanagement.repo.MeetingRepo;
import com.peoplestrong.activitymanagement.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service @RequiredArgsConstructor  @Slf4j @Transactional
public class MeetingServiceImpl implements MeetingService{

    @Autowired
    MeetingRepo meetingRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    MeetingAttendeeRepo meetingAttendeeRepo;

    @Autowired
    MeetingNotFound meetingNotFound;

    @Autowired
    UserNotFound userNotFound;

    @Autowired
    NoAuthority noAuthority;

    @Autowired
    EmailService emailService;

    @Override
    public int addUserToMeeting(Long userid,UserToMeeting userToMeeting) {
        Optional<MeetingAttendee> meetingAttendee=meetingAttendeeRepo.findByMeetingIdAndUserId(userToMeeting.getMeetingId(),userToMeeting.getUserId());
        Optional<User> user=userRepo.findById(userToMeeting.getUserId());
        Optional<Meeting> meeting=meetingRepo.findById(userToMeeting.getMeetingId());

        if(meetingAttendee.isPresent())
        {
            log.error("User is already invited for the meeting");
            return 3;
        }
        if(!user.isPresent())
        {
            log.error("User not found in db");
            return 2;
        }
        if(!meeting.isPresent())
        {
            log.error("Meeting doesn't exist.");
            return 1;
        }

        try{
            meetingAttendeeRepo.save(
                    new MeetingAttendee(new MeetingAttendeeKey(user.get().getId(),meeting.get().getId()),
                            user.get(),
                            meeting.get(),
                            userToMeeting.getStatus()
                    ));

            String mail=user.get().getUsername();
            //sender,subject,message,to
            emailService.sendEmail("Meeting Scheduled","New meeting assigned",mail);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    @Override
    public int updateMeeting(Long userid,Meeting meeting) {
        Optional<Meeting> meetingfromdb=meetingRepo.findById(meeting.getId());

        if(!meetingfromdb.isPresent())
        {
            return 1;
        }
        if(!userRepo.existsById(meeting.getCreator()))
        {
            return 2;
        }
        if(!Objects.equals(userid, meetingfromdb.get().getCreator()))
        {
            return 5;
        }
        meetingfromdb.get().setCreator(meeting.getCreator());
        meetingfromdb.get().setStartTime(meeting.getStartTime());
        meetingfromdb.get().setEndTime(meeting.getEndTime());
        meetingfromdb.get().setDescription(meeting.getDescription());
        meetingfromdb.get().setPurpose(meeting.getPurpose());
        meetingfromdb.get().setPlace(meeting.getPlace());

        meetingRepo.save(meetingfromdb.get());

        return 0;
    }

    @Override
    public int updateMeetingTime(Long userid,Meeting meeting) {
        Optional<Meeting> meetingfromdb=meetingRepo.findById(meeting.getId());

        if(!meetingfromdb.isPresent())
        {
            return 1;
        }
        if(!userRepo.existsById(meeting.getCreator()))
        {
            return 2;
        }
        if(!Objects.equals(userid, meetingfromdb.get().getCreator()))
        {
            return 5;
        }
        meetingfromdb.get().setStartTime(meeting.getStartTime());
        meetingRepo.save(meetingfromdb.get());

        return 0;
    }

    @Override
    public int updateMeetingDescription(Long userid,Meeting meeting) {
        Optional<Meeting> meetingfromdb=meetingRepo.findById(meeting.getId());

        if(!meetingfromdb.isPresent())
        {
            return 1;
        }
        if(!userRepo.existsById(meeting.getCreator()))
        {
            return 2;
        }
        if(!Objects.equals(userid, meetingfromdb.get().getCreator()))
        {
            return 5;
        }
        meetingfromdb.get().setDescription(meeting.getDescription());
        meetingRepo.save(meetingfromdb.get());

        return 0;
    }

    @Override
    public int updateMeetingPurpose(Long userid,Meeting meeting) {
        Optional<Meeting> meetingfromdb=meetingRepo.findById(meeting.getId());

        if(!meetingfromdb.isPresent())
        {
            return 1;
        }
        if(!userRepo.existsById(meeting.getCreator()))
        {
            return 2;
        }
        if(!Objects.equals(userid, meetingfromdb.get().getCreator()))
        {
            return 5;
        }
        meetingfromdb.get().setPurpose(meeting.getPurpose());
        meetingRepo.save(meetingfromdb.get());

        return 0;
    }

    @Override
    public int updateMeetingPlace(Long userid,Meeting meeting) {
        Optional<Meeting> meetingfromdb=meetingRepo.findById(meeting.getId());

        if(!meetingfromdb.isPresent())
        {
            return 1;
        }
        if(!userRepo.existsById(meeting.getCreator()))
        {
            return 2;
        }
        if(!Objects.equals(userid, meetingfromdb.get().getCreator()))
        {
            return 5;
        }
        meetingfromdb.get().setPlace(meeting.getPlace());
        meetingRepo.save(meetingfromdb.get());

        return 0;
    }

    @Override
    public int updateMeetingStatus(Long userid, UserToMeeting userToMeeting) {
        Optional<Meeting> meetingfromdb=meetingRepo.findById(userToMeeting.getMeetingId());
        if(!meetingfromdb.isPresent())
        {
            return 1;
        }
        if(!userRepo.existsById(userid))
        {
            return 2;
        }
        Optional<MeetingAttendee> meetingAttendeefromdb=meetingAttendeeRepo.findByMeetingIdAndUserId(
                meetingfromdb.get().getId(),
                userid
                );
        if(!Objects.equals(meetingfromdb.get().getCreator(), userid))
            return 5;
        if(meetingAttendeefromdb.isPresent())
        {
            meetingAttendeefromdb.get().setStatus(userToMeeting.getStatus());
            meetingAttendeeRepo.save(meetingAttendeefromdb.get());
            return 0;
        }
        else
            return 4;
    }

    @Override
    public int deleteMeetingById(Long meetingId,Long userid) {

        Optional<Meeting> meeting=meetingRepo.findById(meetingId);
        if(!meeting.isPresent())
        {
            return 1;
        }
        if(!Objects.equals(meeting.get().getCreator(), userid))
            return 2;
        meetingRepo.deleteById(meetingId);
        return 0;
    }

    @Override
    public int deleteUserFromMeeting(DeleteUserFromMeeting deleteUserFromMeeting, Long userid) {
        Optional<Meeting> meeting=meetingRepo.findById(deleteUserFromMeeting.getMeetingId());
        if(!meeting.isPresent())
        {
            return 1;
        }
        if(!Objects.equals(meeting.get().getCreator(), userid))
            return 2;
        if(!userRepo.existsById(deleteUserFromMeeting.getUserToBeDeleted()))
            return 3;
        Optional<MeetingAttendee> meetingAttendee=meetingAttendeeRepo.findByMeetingIdAndUserId(deleteUserFromMeeting.getMeetingId(),deleteUserFromMeeting.getUserToBeDeleted());

        if(!meetingAttendee.isPresent())
            return 4;
        try{
            meetingAttendeeRepo.deleteByUserIdAndMeetingId(deleteUserFromMeeting.getUserToBeDeleted(),deleteUserFromMeeting.getMeetingId());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    @Override
    public ResponseEntity<?> findMeetingByCreatorid(Long userid) {
        Optional<User> user=userRepo.findById(userid);
        if(!user.isPresent())
        {
            return ResponseEntity.badRequest().body(userNotFound);
        }
        List<Meeting> meetingCreatedbyuserList = meetingRepo.findByCreator(userid);
        List<MeetingFromCreator> meetingCreated=new ArrayList<>();

        for(Meeting meetingCreatedbyuser:meetingCreatedbyuserList)
        {
            AtomicInteger acceptedCount= new AtomicInteger(0);
            AtomicInteger rejectedCount= new AtomicInteger(0);
            List<UserMeetingStatus> userMeetingStatuses=new ArrayList<>();
            meetingCreatedbyuser.getMeetingAttendees().forEach(meetingAssignee1 -> {
                userMeetingStatuses.add(new UserMeetingStatus(meetingAssignee1.getUser().getUsername(),
                                meetingAssignee1.getUser().getName(),
                                meetingAssignee1.getStatus()
                        )
                );
                if(meetingAssignee1.getStatus().equals("Accepted"))
                {
                    acceptedCount.getAndIncrement();
                }
                else if(meetingAssignee1.getStatus().equals("Rejected"))
                {
                    rejectedCount.getAndIncrement();
                }
            });
            meetingCreated.add(new MeetingFromCreator(meetingCreatedbyuser.getId(),
                    meetingCreatedbyuser.getPurpose(),
                    userid,
                    meetingCreatedbyuser.getStartTime(),
                    meetingCreatedbyuser.getEndTime(),
                    meetingCreatedbyuser.getDescription(),
                    userMeetingStatuses,
                    (long) acceptedCount.get(),
                    (long) rejectedCount.get(),
                    user.get().getName()
            ));
        }
        meetingCreated.sort((o1, o2) -> o1.getStartTime().compareTo(o2.getStartTime()));

        return ResponseEntity.ok().body(meetingCreated);
    }

    @Override
    public ResponseEntity<?> getAllMeetingsByUserid(Long userid) {
        Optional<User> user=userRepo.findById(userid);
        if(!user.isPresent())
        {
            return ResponseEntity.badRequest().body(userNotFound);
        }
        List<Meeting> meetingCreatedbyuserList = meetingRepo.findByCreator(userid);
        List<MeetingFromCreator> meetingCreated=new ArrayList<>();

        for(Meeting meetingCreatedbyuser:meetingCreatedbyuserList)
        {
            AtomicInteger acceptedCount= new AtomicInteger(0);
            AtomicInteger rejectedCount= new AtomicInteger(0);
            List<UserMeetingStatus> userMeetingStatuses=new ArrayList<>();
            meetingCreatedbyuser.getMeetingAttendees().forEach(meetingAssignee1 -> {
                userMeetingStatuses.add(new UserMeetingStatus(meetingAssignee1.getUser().getUsername(),
                                meetingAssignee1.getUser().getName(),
                                meetingAssignee1.getStatus()
                        )
                );
                if(meetingAssignee1.getStatus().equals("Accepted"))
                {
                    acceptedCount.getAndIncrement();
                }
                else if(meetingAssignee1.getStatus().equals("Rejected"))
                {
                    rejectedCount.getAndIncrement();
                }
            });
            meetingCreated.add(new MeetingFromCreator(meetingCreatedbyuser.getId(),
                    meetingCreatedbyuser.getPurpose(),
                    userid,
                    meetingCreatedbyuser.getStartTime(),
                    meetingCreatedbyuser.getEndTime(),
                    meetingCreatedbyuser.getDescription(),
                    userMeetingStatuses,
                    (long) acceptedCount.get(),
                    (long) rejectedCount.get(),
                    user.get().getName()
            ));
        }
        meetingCreated.sort((o1, o2) -> o1.getStartTime().compareTo(o2.getStartTime()));

        List<MeetingAttendee> meetingAttendeeList=meetingAttendeeRepo.findByUserId(userid);
        List<MeetingFromMeetingAttendee> meetingAttend=new ArrayList<>();
        for(MeetingAttendee meetingAttendee:meetingAttendeeList)
        {
            Meeting meeting=meetingAttendee.getMeeting();
            meetingAttend.add(new MeetingFromMeetingAttendee(meeting.getId(),
                    meeting.getPurpose(),
                    meeting.getCreator(),
                    meeting.getStartTime(),
                    meeting.getEndTime(),
                    meeting.getDescription(),
                    meetingAttendee.getStatus(),
                    userRepo.findById(meeting.getCreator()).get().getName()
            ));
        }
        meetingAttend.sort((o1, o2) -> o1.getStartTime().compareTo(o2.getStartTime()));

        Map<String,Object> allMeeting=new HashMap<String,Object>();
        allMeeting.put("created",meetingCreated);
        allMeeting.put("assigned",meetingAttend);
        return ResponseEntity.ok().body(allMeeting);
    }

    @Override
    public ResponseEntity<?> findAllNonInvitedUsers(Long userId, Long meetingId) {
        Optional<Meeting> meetingfromdb=meetingRepo.findById(meetingId);
        if(!meetingfromdb.isPresent())
        {
            return ResponseEntity.badRequest().body(meetingNotFound);
        }

        Optional<User> userfromdb=userRepo.findById(userId);
        if(!userfromdb.isPresent())
        {
            return ResponseEntity.badRequest().body(userNotFound);
        }

        if(!Objects.equals(meetingfromdb.get().getCreator(), userId))
        {
            return ResponseEntity.badRequest().body(noAuthority);
        }

        String username=userfromdb.get().getUsername();
        int orgStartIndex=username.indexOf('@');

        String orgName=username.substring(orgStartIndex);

        List<User> usersfromdb=userRepo.findByUsernameEndsWith(orgName);

        List<UserNameEmail> userNameEmailsList=new ArrayList<>();

        usersfromdb.forEach(user -> {
            if(!meetingAttendeeRepo.findByMeetingIdAndUserId(meetingId,user.getId()).isPresent())
            {
                userNameEmailsList.add(new UserNameEmail(user.getId(), user.getUsername(),user.getName()));
            }
        });
        return ResponseEntity.ok().body(userNameEmailsList);
    }
}
