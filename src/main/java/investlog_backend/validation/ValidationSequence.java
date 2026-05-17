package investlog_backend.validation;

import jakarta.validation.GroupSequence;

@GroupSequence(value = {ValidationGroups.NotBlankGroups.class, ValidationGroups.RangeGroups.class, ValidationGroups.PatternGroups.class})
public interface ValidationSequence {

}
