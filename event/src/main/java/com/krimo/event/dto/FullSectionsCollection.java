package com.karan.event.dto;

import com.karan.event.data.Section;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullSectionsCollection {

    private Collection<Section> sections;
}
