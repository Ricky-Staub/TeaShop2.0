package com.example.jwt.core.generic;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@MappedSuperclass
public abstract class ExtendedEntity {

//    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(
//            name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator",
//            parameters = {
//                    @org.hibernate.annotations.Parameter(
//                            name = "uuid_gen_strategy_class",
//                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
//                    )
//            }
//    )
//    @Column(name = "id", updatable = false, nullable = false)
//    private UUID id;


    @Id
   @Column(name = "id", updatable = false, nullable = false)
   @Type(type = "uuid-char") // For testing purposes only. H2 does not support binary UUID.
    private UUID id = UUID.randomUUID();

    protected ExtendedEntity() {
    }

    protected ExtendedEntity(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public Object setId(UUID id) {
        this.id = id;
        return null;
    }
}