package com.csed.Mail.mappers;

import java.util.List;

public interface Mapper<A, B> {
    B mapToDto(A a);
    A mapFromDto(B b);
    List<B> mapListToDto(List<A> a);
    List<A> mapListFromDto(List<B> b);
}
