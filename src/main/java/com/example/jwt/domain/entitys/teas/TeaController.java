package com.example.jwt.domain.entitys.teas;

import com.example.jwt.domain.entitys.teas.dto.TeaMapper;
import com.example.jwt.domain.entitys.teas.dto.TeaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("/product")
@RestController
public class TeaController {
private TeaServiceImpl teaServiceImpl;

private TeaMapper teaMapper;

    @Autowired
    public TeaController(TeaServiceImpl teaServiceImpl, TeaMapper teaMapper){
        this.teaServiceImpl = teaServiceImpl;
        this.teaMapper = teaMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeaDTO> findBYId(@PathVariable("id") UUID id) {
        Tea tea = teaServiceImpl.findById(id);
        return new ResponseEntity<>(teaMapper.teaToTeaDTOWithoutID(tea),HttpStatus.OK);
    }

    @GetMapping("/tea")
    public  List<TeaDTO> findAll(){
        return teaServiceImpl.findAll().stream().map(tea -> teaMapper.teaToTeaDTOWithoutID(tea))
                .collect(Collectors.toList());
    }
}