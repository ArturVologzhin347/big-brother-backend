package com.ithub.bigbrotherbackend.student.exception

import com.ithub.bigbrotherbackend.error.ApiException

class StudentNotAssociatedException(studentId: String) :
    ApiException("Student with id $studentId not associated with respondent.")