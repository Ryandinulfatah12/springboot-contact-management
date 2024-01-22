package dinulfatahryan.belajar.springboot.restfull.api.controller;

import dinulfatahryan.belajar.springboot.restfull.api.entity.User;
import dinulfatahryan.belajar.springboot.restfull.api.model.*;
import dinulfatahryan.belajar.springboot.restfull.api.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContactController {
    @Autowired
    private ContactService contactService;

    @PostMapping(
            path = "/api/contacts",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ContactResponse> create(User user, @RequestBody CreateContactRequest request) {
        ContactResponse response = contactService.create(user, request);
        return WebResponse.<ContactResponse>builder().data(response).build();
    }

    @PutMapping(
            path = "/api/contacts/{contactId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ContactResponse> update(User user,
                                               @RequestBody UpdateContactRequest request,
                                               @PathVariable String contactId) {
        request.setId(contactId);
        ContactResponse response = contactService.update(user, request);
        return WebResponse.<ContactResponse>builder().data(response).build();
    }

    @GetMapping(
            path = "/api/contacts/{contactId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ContactResponse> get(User user, @PathVariable String contactId) {
        ContactResponse response = contactService.get(user, contactId);
        return WebResponse.<ContactResponse>builder().data(response).build();
    }

    @GetMapping(
            path = "/api/contacts",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<ContactResponse>> search(User user,
                                               @RequestParam(required = false) String name,
                                               @RequestParam(required = false) String email,
                                               @RequestParam(required = false) String phone,
                                               @RequestParam(required = false, defaultValue = "0") Integer page,
                                               @RequestParam(required = false, defaultValue = "10") Integer size) {
        SearchContactRequest request = SearchContactRequest.builder()
                .page(page)
                .email(email)
                .name(name)
                .phone(phone)
                .size(size)
                .build();
        Page<ContactResponse> responses = contactService.search(user, request);
        return WebResponse.<List<ContactResponse>>builder()
                .data(responses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(responses.getNumber())
                        .totalPage(responses.getTotalPages())
                        .size(responses.getSize())
                        .build())
                .build();
    }

    @DeleteMapping(
            path = "/api/contacts/{contactId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(User user, @PathVariable String contactId) {
        contactService.delete(user, contactId);
        return WebResponse.<String>builder().data("OK").build();
    }

}
