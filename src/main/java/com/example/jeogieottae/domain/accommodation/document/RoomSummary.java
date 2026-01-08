package com.example.jeogieottae.domain.accommodation.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Getter
@NoArgsConstructor
public class RoomSummary {

    @Field(type = FieldType.Long)
    private Long roomId;

    @Field(type = FieldType.Long)
    private Long price;

    @Field(type = FieldType.Integer)
    private Integer maxGuest;

    @Field(type = FieldType.Keyword)
    private List<String> reservedDates;

    public RoomSummary(Long roomId, Long price, Integer maxGuest, List<String> reservedDates) {
        this.roomId = roomId;
        this.price = price;
        this.maxGuest = maxGuest;
        this.reservedDates = reservedDates;
    }
}