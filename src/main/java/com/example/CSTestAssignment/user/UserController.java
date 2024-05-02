package com.example.CSTestAssignment.user;

import com.example.CSTestAssignment.user.response.ResponseService;
import com.example.CSTestAssignment.user.response.ResponseUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {
    private final ResponseService responseService;
   @GetMapping("/list")
    public ResponseEntity<ResponseUserDTO> getAllUsers(){
        return responseService.getAllUsersResponse();

    }
    @GetMapping("/{id}")
    public ResponseEntity <ResponseUserDTO> getUser(@PathVariable long id){
       return responseService.getUserByIdResponse(id);
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseUserDTO>  createNewUser (@RequestBody UserDTO userDTO)  {
       return responseService.saveUserResponse(userDTO);

    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseUserDTO>  updateUser(@PathVariable long id,@RequestBody UserDTO user){
        return responseService.updateUserByIdResponse(id,user);

    }
    @PatchMapping ("/update-some-fields/{id}")
    public ResponseEntity<ResponseUserDTO> updateSomeFields(@PathVariable long id, @RequestBody String jsonDataForChange) {
       return responseService.updateUserFieldsResponse(id, jsonDataForChange);

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseUserDTO>deleteUser(@PathVariable long id){
      return  responseService.deleteUserByIdResponse(id);
    }

    @GetMapping("/search-by-age")
    public ResponseEntity<ResponseUserDTO> searchByAge(@RequestParam ("from")String fromDate, @RequestParam("to") String  toDate){
       return responseService.searchByBirthdayFromToResponse(fromDate,toDate);
    }

}
