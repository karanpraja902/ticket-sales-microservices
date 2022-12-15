package com.karan.ticket.dto;

import com.karan.ticket.data.Section;
import lombok.Data;

import java.util.Collection;

@Data
public class FullSectionsCollection {

    private Collection<Section> sections;
}
