package com.nhatle.workmangement.until.api

import com.nhatle.workmangement.data.model.*
import com.nhatle.workmangement.data.model.response.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {

    @POST(value = "/login")
    fun login(
        @Body request: LoginResponse?
    ): Call<BaseResponse<UserProfile>>

    @POST(value = "/register")
    fun register(
        @Body register: RegisterResponse?
    ): Call<BaseResponse<UserProfile>>

    @POST(value = "/updateProfile")
    fun updateProfile(
        @Body profile: UserProfile?
    ): Call<BaseResponse<UserProfile>>

    @GET(value = "/getInfoProfile")
    fun getInfoProfile(@Query("id") id: Int): Call<UserProfile>

    @GET(value = "/getAllFriendByUser")
    fun getAllFriendByUser(@Query("userId") userId: Int):
            Call<ArrayList<FriendResponse>>

    @GET(value = "/getAllNotFriend")
    fun getAllNotFriend(@Query("idUser") idUser: Int):
            Call<ArrayList<UserProfile>>

    @POST(value = "/acceptedFriend")
    fun acceptedFriend(@Body invitationFriend: InvitationFriend): Call<Boolean>

    @GET(value = "/getAllUserSenderFriend")
    fun getAllUserSenderFriend(@Query("idProfile") idProfile: Int):
            Call<ArrayList<FriendResponse>>

    @POST(value = "/senderFriend")
    fun senderFriend(@Body friend: InvitationFriend)
            : Call<BaseResponse<InvitationFriend>>

    @GET(value = "/getAllWorkWithUserIsMember")
    fun getAllWorkWithUserIsMember(@Query("profileId") profileId: Int):
            Call<ArrayList<ActionResponse>>

    @POST(value = "/addWork")
    fun addWork(@Body action: Action): Call<BaseResponse<Action>>

    @POST(value = "/updateWork")
    fun updateWork(@Body action: Action): Call<BaseResponse<Action>>

    @POST(value = "/deleteWork")
    fun deleteWork(@Query("idW") idW: Int, @Query("profileId") profileId: Int): Call<Boolean>

    @GET(value = "/getAllMemberOnActionInGroup")
    fun getAllMemberOnActionInGroup(
        @Query("groupMakeAction") groupMakeAction: Int,
        @Query("actionId") actionId: Int
    ):
            Call<ArrayList<UserTeamResponse>>

    @POST(value = "/deleteGroup")
    fun deleteGroup(@Query("groupId") groupId: Int): Call<Boolean>

    @POST(value = "/addGroup")
    fun addGroup(@Body team: Team): Call<BaseResponse<Team>>

    @POST(value = "/addMemberForGroup")
    fun addMemberForGroup(@Body listUserTeam: List<UserTeam>): Call<List<BaseResponse<UserTeam>>>

    @POST(value = "/deleteMemberOnGroup")
    fun deleteMemberOnGroup(@Body userTeam: UserTeam): Call<Boolean>

    @GET(value = "/getAllActionSmallByAction")
    fun getAllActionSmallByAction(@Query("actionId") actionId: Int):
            Call<ArrayList<ActionSmall>>

    @POST(value = "/addActionSmall")
    fun addActionSmall(@Body listActionSmall: List<ActionSmall>):
            Call<List<BaseResponse<ActionSmall>>>

    @POST(value = "/deleteActionSmall")
    fun deleteActionSmall(@Query("actionSmallId") actionSmallId: Int):
            Call<Boolean>

    @GET(value = "/getAllUserMakeActionSmall")
    fun getAllUserMakeActionSmall(@Query("actionId") actionId: Int):
            Call<ArrayList<UserActionSmallResponse>>

    @POST(value = "/addUserActionSmall")
    fun addUserActionSmall(@Body userActionSmall: UserActionSmall):
            Call<BaseResponse<UserActionSmall>>

    @POST(value = "/deleteUserActionSmall")
    fun deleteUserActionSmall(
        @Query("userActionSmallId") userActionSmallId: Int
    ):
            Call<Boolean>

    @POST(value = "/updateUserActionSmall")
    fun updateUserActionSmall(@Body userActionSmall: UserActionSmall):
            Call<BaseResponse<UserActionSmall>>

    @POST(value = "/deleteInvitationFriend")
    fun deleteInvitationFriend(@Query("friendId") friendId: Int): Call<Boolean>

    @POST(value = "/cancelInvitationFriend")
    fun cancelInvitationFriend(
        @Query("senderId") senderId: Int,
        @Query("receiverId") receiverId: Int
    ): Call<Boolean>

    @GET(value = "/getAllActionSmallOnActionOfUser")
    fun getAllActionSmallOnActionOfUser(
        @Query("actionId") actionId: Int,
        @Query("profileId") profileId: Int
    ) :Call<ArrayList<ActionSmall>>

    @GET(value = "/getAllReportOnAction")
    fun getAllReportOnAction(@Query("actionId") actionId: Int):
            Call<ArrayList<UserActionReportResponse>>

    @POST(value = "/addReportOnAction")
    fun addReportOnAction(@Body userActionReport: UserActionReport):
            Call<BaseResponse<UserActionReport>>

    @POST(value = "/updateReportOnAction")
    fun updateReportOnAction(@Body userActionReport: UserActionReport):
            Call<BaseResponse<UserActionReport>>

    @POST(value = "/deleteReportOnAction")
    fun deleteReportOnAction(@Query("reportId") reportId: Int):
            Call<Boolean>

    @POST(value = "/sendComment")
    fun sendComment(@Body comment: Comment):
            Call<BaseResponse<Comment>>

    @POST(value = "/updateStatusWork")
    fun updateStatusWork(
        @Query("id") id: Int
        , @Query("status") status: String
    ): Call<Boolean>

    @POST(value = "/deleteCommentOnAction")
    fun deleteCommentOnAction(
        @Query("commentId") commentId: Int,
        @Query("profileId") profileId: Int
    ):
            Call<Boolean>

    @GET(value = "/getAllCommentOnAction")
    fun getAllCommentOnAction(@Query("actionId") actionId: Int): Call<ArrayList<CommentResponse>>

    @GET(value = "/getAllActionSmallOfUser")
    fun getAllActionSmallOfUser(
        @Query("actionId") actionId: Int,
        @Query("profileId") profileId: Int
    ): Call<ArrayList<UserActionSmallResponse>>
}