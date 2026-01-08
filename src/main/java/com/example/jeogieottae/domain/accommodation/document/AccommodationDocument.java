package com.example.jeogieottae.domain.accommodation.document;

import com.example.jeogieottae.domain.accommodation.enums.AccommodationType;
import com.example.jeogieottae.domain.accommodation.enums.City;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "accommodations")
@Setting(settingPath = "elasticsearch/settings.json")
public class AccommodationDocument {

    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "nori_analyzer")
    private String name;

    @Field(type = FieldType.Keyword)
    private AccommodationType type;

    @Field(type = FieldType.Keyword)
    private City location;

    @Field(type = FieldType.Double)
    private double rating;

    @Field(type = FieldType.Long)
    private Long viewCount;

    @Field(type = FieldType.Nested)
    private List<RoomSummary> rooms;

    public AccommodationDocument(Long id, String name, AccommodationType type, City location, double rating, Long viewCount, List<RoomSummary> rooms) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.location = location;
        this.rating = rating;
        this.viewCount = viewCount;
        this.rooms = rooms;
    }
}