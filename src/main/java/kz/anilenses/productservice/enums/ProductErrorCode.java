package kz.anilenses.productservice.enums;

import kz.anilenses.exceptionhandler.LocalizedErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProductErrorCode implements LocalizedErrorCode {

    PRODUCT_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND);

    private final HttpStatus errorStatus;

}
