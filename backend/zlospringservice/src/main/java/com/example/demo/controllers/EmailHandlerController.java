//package com.example.demo.controllers;
//
//import com.example.demo.data.EmailHandlerVO;
//import com.example.demo.services.EmailHandlerServices;
//import com.example.demo.util.MediaType;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.ArraySchema;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.hateoas.PagedModel;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.logging.Logger;
//
//@RestController
//@RequestMapping("/api/emailhandler/")
//@Tag(name = "Email", description = "Endpoints for Managing Emails")
//public class EmailHandlerController {
//
//    private Logger logger = Logger.getLogger(EmailHandlerServices.class.getName());
//
////    @Autowired
//    private EmailHandlerServices service;
//
//    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
//    @Operation(summary = "Finds all EmailHandlers", description = "Finds all EmailHandlers",
//            tags = {"EmailHandlers"},
//            responses = {
//                    @ApiResponse(description = "Success", responseCode = "200",
//                            content = {
//                                    @Content(
//                                            mediaType = "application/json",
//                                            array = @ArraySchema(schema = @Schema(implementation = EmailHandlerVO.class))
//                                    )
//                            }),
//                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
//                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
//                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
//                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
//            })
//    public ResponseEntity<PagedModel<EntityModel<EmailHandlerVO>>> findAll(
//            @RequestParam(value = "page", defaultValue = "0") Integer page,
//            @RequestParam(value = "size", defaultValue = "12") Integer size,
//            @RequestParam(value = "direction", defaultValue = "asc") String direction
//    ) {
//        var sortDirection = "desc".equals(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
//
//        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "emailCode"));
//
//        return ResponseEntity.ok(service.findAll(pageable));
//    }
//
//    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
//    @Operation(summary = "Finds an EmailHandler", description = "Finds an EmailHandler",
//            tags = {"EmailHandler"},
//            responses = {
//                    @ApiResponse(description = "Success", responseCode = "200",
//                            content = @Content(schema = @Schema(implementation = EmailHandlerVO.class))
//                    ),
//                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
//                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
//                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
//                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
//                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
//            })
//    public ResponseEntity<EmailHandlerVO> findById(@PathVariable(value = "id") Integer id) {
//        EmailHandlerVO emailHandlerVO = service.findById(id);
//        return ResponseEntity.ok(emailHandlerVO);
//    }
//
//    @PostMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
//            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
//    @Operation(summary = "Adds a new EmailHandler", description = "Adds a new EmailHandler by passing in a JSON, XML or YML representation of the EmailHandler!",
//            tags = {"EmailHandlers"},
//            responses = {
//                    @ApiResponse(description = "Success", responseCode = "200",
//                            content = @Content(schema = @Schema(implementation = EmailHandlerVO.class))
//                    ),
//                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
//                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
//                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
//            })
//    public ResponseEntity<EmailHandlerVO> create(@RequestBody EmailHandlerVO emailHandlerVO) {
//        EmailHandlerVO createdVO = service.create(emailHandlerVO);
//        return ResponseEntity.ok(createdVO);
//    }
//
//    @GetMapping(value = "/verifyEmailCode")
//    @Operation(summary = "Verifies an EmailHandler", description = "Verifies an EmailHandler by passing in the email address and verification code!",
//            tags = {"EmailHandlers"},
//            responses = {
//                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
//                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
//                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
//                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
//                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
//            })
//    public ResponseEntity<?> verifyEmailCode(@RequestParam(value = "email") String email, @RequestParam(value = "code") int code) {
//        boolean isCodeValid = service.verifyEmailCode(email, code);
//        if(isCodeValid) {
//            return ResponseEntity.ok().body("Verification successful.");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid code or email.");
//        }
//    }
//}
