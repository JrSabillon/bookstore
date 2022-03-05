package com.test.bookstore.models.POJO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Plain old java object para acceder a los datos del request
 * y no afectar la entidad que guarda los datos en la base.
 */
@Getter
@Setter
@NoArgsConstructor
public class LikePojo {
    public Long bookId;
    public String customerEmail;
}
