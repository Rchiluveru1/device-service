package com.codingtask.entity;

import org.springframework.data.aerospike.annotation.Indexed;
import org.springframework.data.aerospike.mapping.Document;
import org.springframework.data.aerospike.mapping.Field;
import org.springframework.data.annotation.Id;

import com.aerospike.client.query.IndexCollectionType;
import com.aerospike.client.query.IndexType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@Document(collection = "devices") // <2>
@AllArgsConstructor
public class Device {
    @Id
    @Indexed(type = IndexType.STRING, collectionType = IndexCollectionType.DEFAULT)
    private Long id;
    @Field
    @Indexed(type = IndexType.STRING, collectionType = IndexCollectionType.DEFAULT)
    private String osName;
    @Field
    private String osVersion;
    @Field
    private String browserName;
    @Field
    private String browserVersion;
    @Field
    private int hitCount;

}
