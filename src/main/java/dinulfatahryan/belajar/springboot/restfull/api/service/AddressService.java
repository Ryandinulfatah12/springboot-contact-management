package dinulfatahryan.belajar.springboot.restfull.api.service;

import dinulfatahryan.belajar.springboot.restfull.api.entity.Address;
import dinulfatahryan.belajar.springboot.restfull.api.entity.Contact;
import dinulfatahryan.belajar.springboot.restfull.api.entity.User;
import dinulfatahryan.belajar.springboot.restfull.api.model.AddressResponse;
import dinulfatahryan.belajar.springboot.restfull.api.model.CreateAddressRequest;
import dinulfatahryan.belajar.springboot.restfull.api.model.UpdateAddressRequest;
import dinulfatahryan.belajar.springboot.restfull.api.repository.AddressRepository;
import dinulfatahryan.belajar.springboot.restfull.api.repository.ContactRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private ContactRepository contactRepository;

    @Transactional
    public AddressResponse create(User user, CreateAddressRequest request) {
        validationService.validate(request);
        Contact contact = contactRepository.findFirstByUserAndId(user, request.getContactId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        Address address = new Address();
        address.setId(UUID.randomUUID().toString());
        address.setContact(contact);
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setCountry(request.getCountry());
        address.setProvince(request.getProvince());
        address.setPostalCode(request.getPostalCode());

        addressRepository.save(address);
        return toAddressResponse(address);
    }

    private AddressResponse toAddressResponse(Address address) {
        return AddressResponse.builder()
                .id(address.getId())
                .city(address.getCity())
                .country(address.getCountry())
                .postalCode(address.getPostalCode())
                .province(address.getProvince())
                .street(address.getStreet())
                .build();
    }

    public AddressResponse get(User user, String contactId, String addressId) {
        Contact contact = contactRepository.findFirstByUserAndId(user, contactId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        Address address = addressRepository.findFirstByContactAndId(contact, addressId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));
        return toAddressResponse(address);
    }

    @Transactional
    public AddressResponse update(User user, UpdateAddressRequest request) {
        validationService.validate(request);

        Contact contact = contactRepository.findFirstByUserAndId(user, request.getContactId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        Address address = addressRepository.findFirstByContactAndId(contact, request.getAddressId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));

        address.setStreet(request.getStreet());
        address.setPostalCode(request.getPostalCode());
        address.setCity(request.getCity());
        address.setProvince(request.getProvince());
        address.setCountry(request.getCountry());
        addressRepository.save(address);

        return toAddressResponse(address);
    }

    @Transactional
    public void remove(User user, String contactId, String addressId) {
        Contact contact = contactRepository.findFirstByUserAndId(user, contactId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        Address address = addressRepository.findFirstByContactAndId(contact, addressId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));

        addressRepository.delete(address);

    }

    public List<AddressResponse> list(User user, String contactId) {
        Contact contact = contactRepository.findFirstByUserAndId(user, contactId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        List<Address> address = addressRepository.findAllByContact(contact);
        return address.stream().map(this::toAddressResponse).toList();
    }
}
