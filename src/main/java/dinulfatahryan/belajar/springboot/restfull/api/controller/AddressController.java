package dinulfatahryan.belajar.springboot.restfull.api.controller;

import dinulfatahryan.belajar.springboot.restfull.api.entity.User;
import dinulfatahryan.belajar.springboot.restfull.api.model.AddressResponse;
import dinulfatahryan.belajar.springboot.restfull.api.model.CreateAddressRequest;
import dinulfatahryan.belajar.springboot.restfull.api.model.UpdateAddressRequest;
import dinulfatahryan.belajar.springboot.restfull.api.model.WebResponse;
import dinulfatahryan.belajar.springboot.restfull.api.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping(
            path = "/api/contacts/{contactId}/addresses",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<AddressResponse>> list(User user,
                                                   @PathVariable("contactId") String contactId) {
        List<AddressResponse> response = addressService.list(user, contactId);

        return WebResponse.<List<AddressResponse>>builder()
                .data(response)
                .build();
    }

    @PostMapping(
            path = "/api/contacts/{contactId}/addresses",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AddressResponse> create(User user,
                                               @RequestBody CreateAddressRequest request,
                                               @PathVariable("contactId") String contactId) {
        request.setContactId(contactId);
        AddressResponse response = addressService.create(user, request);
        return WebResponse.<AddressResponse>builder()
                .data(response)
                .build();
    }

    @GetMapping(
            path = "/api/contacts/{contactId}/addresses/{addressId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AddressResponse> get(User user,
                                               @PathVariable("addressId") String addressId,
                                               @PathVariable("contactId") String contactId) {
        AddressResponse response = addressService.get(user, contactId, addressId);
        return WebResponse.<AddressResponse>builder()
                .data(response)
                .build();
    }

    @PutMapping(
            path = "/api/contacts/{contactId}/addresses/{addressId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AddressResponse> update(User user,
                                               @RequestBody UpdateAddressRequest request,
                                               @PathVariable("contactId") String contactId,
                                               @PathVariable("addressId") String addressId) {
        request.setContactId(contactId);
        request.setAddressId(addressId);
        AddressResponse response = addressService.update(user, request);
        return WebResponse.<AddressResponse>builder()
                .data(response)
                .build();
    }

    @DeleteMapping(
            path = "/api/contacts/{contactId}/addresses/{addressId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> remove(User user,
                                            @PathVariable("addressId") String addressId,
                                            @PathVariable("contactId") String contactId) {
        addressService.remove(user, contactId, addressId);
        return WebResponse.<String>builder()
                .data("OK")
                .build();
    }


}
