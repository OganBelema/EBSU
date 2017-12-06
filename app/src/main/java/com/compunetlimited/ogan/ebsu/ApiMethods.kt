package com.compunetlimited.ogan.ebsu

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by belema on 10/29/17.
 */

interface ApiMethods {

    @POST("studentApi/checkPayment")
    fun checkPayment(@Query("studentId") studentId: String?):  Call<Boolean>

    /* @POST("AccomodationApi/CheckHostel")
    Call<ResponseBody> checkRoom(@Query("studentId") String studentId);*/

    @POST("AccountApi/SignUp")
    fun checkStudentId(@Query("id") id: String): Call<Student>

    /* @POST("eclassroomapi/mycourses")
    Call<ArrayList<MyCourses>> getStudentCourses(@Query("studentId") String studentId);*/

    @POST("StudentApi/Dashboard")
    fun getDashboard(@Query("id") id: String?): Call<Dashboard>

    /* @POST("eclassroomapi/getmodules")
    Call<ArrayList<Module>> getModule(@Query("courseID") String courseId);

    @POST("CourseRegistrationApi/GetCourses")
    Call<CourseReg> courseRegistration(@Query("id") String id);*/

    @POST("AccountApi/RegisterStudent")
    fun registerStudent(@Body student: Student): Call<ResponseBody>

    @POST("AccountApi/Login")
    fun login(@Body student: Student): Call<UserId>

    /*@POST("CourseRegistrationApi/RegisterCourse")
    Call<ResponseBody> submitCourseReg(@Body CourseReg courseReg);

    @POST("eclassroomapi/gettopics")
    Call<ArrayList<Topic>> getTopics(@Query("moduleId") String id);

    @POST("eclassroomapi/gettopiccontent")
    Call<ArrayList<TopicContent>> getContent(@Query("topicId") String id);*/
}
