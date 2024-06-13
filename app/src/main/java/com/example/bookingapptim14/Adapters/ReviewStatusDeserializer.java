package com.example.bookingapptim14.Adapters;

import com.example.bookingapptim14.models.dtos.ApproveReviewsDTO.ReviewStatus;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class ReviewStatusDeserializer implements JsonDeserializer<ReviewStatus> {

    @Override
    public ReviewStatus deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return  ReviewStatus.valueOf(json.getAsString());
    }

}
