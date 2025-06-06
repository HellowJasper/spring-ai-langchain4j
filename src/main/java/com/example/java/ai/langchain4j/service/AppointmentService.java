package com.example.java.ai.langchain4j.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.java.ai.langchain4j.entity.Appointment;

public interface AppointmentService extends IService<Appointment> {
    Appointment getOne(Appointment appointment);
}