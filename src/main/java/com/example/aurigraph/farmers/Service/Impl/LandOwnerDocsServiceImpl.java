package com.example.aurigraph.farmers.Service.Impl;

import com.example.aurigraph.farmers.DTO.IssuedDocumentDTO;
import com.example.aurigraph.farmers.DTO.LandOwnerDocsDTO;
import com.example.aurigraph.farmers.Domain.LandOwner;
import com.example.aurigraph.farmers.Domain.LandOwnerDoc;

import com.example.aurigraph.farmers.Repository.LandOwnerDocsRepository;
import com.example.aurigraph.farmers.Service.LandOwnerDocsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class LandOwnerDocsServiceImpl implements LandOwnerDocsService {

    private static final Logger log = LoggerFactory.getLogger(LandOwnerDocsServiceImpl.class);
    @Autowired
    private LandOwnerDocsRepository landOwnerDocsRepository;

    public LandOwnerDocsServiceImpl(LandOwnerDocsRepository landOwnerDocsRepository) {
        this.landOwnerDocsRepository = landOwnerDocsRepository;
    }

    @Override
    public LandOwnerDoc save(LandOwnerDocsDTO landOwnerDocsDTO){
        return null;
    }

    @Override
    public void saveLandDocs(List<LandOwnerDocsDTO> landOwnerDocsDTOS, LandOwner landOwner) {
       for (LandOwnerDocsDTO landOwnerDocDTO : landOwnerDocsDTOS) {
           Optional<LandOwnerDoc> landOwnerDocOpt =landOwnerDocsRepository.findByDocTypeAndLandOwnerId(landOwnerDocDTO.getDoc().getDoctype(),landOwner.getId());
           LandOwnerDoc landOwnerDoc = new LandOwnerDoc();
           IssuedDocumentDTO landOwnerNewDoc = landOwnerDocDTO.getDoc();
           if (landOwnerDocOpt.isEmpty()) {
               if (landOwnerNewDoc.getName() != null)
                   landOwnerDoc.setDocName(landOwnerNewDoc.getName());

               if (landOwnerNewDoc.getDoctype() != null)
                   landOwnerDoc.setDocType(landOwnerNewDoc.getDoctype());

               if (landOwnerNewDoc.getDate() != null)
                   landOwnerDoc.setDate(landOwnerNewDoc.getDate());

               if (landOwnerNewDoc.getDescription() != null)
                   landOwnerDoc.setDescription(landOwnerNewDoc.getDescription());

               if (landOwnerNewDoc.getSize() != null)
                   landOwnerDoc.setSize(landOwnerNewDoc.getSize());

               if (landOwnerNewDoc.getType() != null)
                   landOwnerDoc.setType(landOwnerNewDoc.getType());

               if (landOwnerNewDoc.getMime() != null)
                   landOwnerDoc.setMimes(String.join(",", landOwnerNewDoc.getMime()));

               if (landOwnerNewDoc.getParent() != null)
                   landOwnerDoc.setParent(landOwnerNewDoc.getParent());

               landOwnerDoc.setLandOwner(landOwner);

               if (landOwnerDocDTO.getDocPath() != null)
                   landOwnerDoc.setDocUrl(landOwnerDocDTO.getDocPath());

           } else {
               landOwnerDoc = landOwnerDocOpt.get();

               if (landOwnerDocDTO.getDocPath() != null)
                   landOwnerDoc.setDocUrl(landOwnerDocDTO.getDocPath());

               if (landOwnerNewDoc.getName() != null)
                   landOwnerDoc.setDocName(landOwnerNewDoc.getName());

               if (landOwnerNewDoc.getDate() != null)
                   landOwnerDoc.setDate(landOwnerNewDoc.getDate());

               if (landOwnerNewDoc.getDescription() != null)
                   landOwnerDoc.setDescription(landOwnerNewDoc.getDescription());

               if (landOwnerNewDoc.getSize() != null)
                   landOwnerDoc.setSize(landOwnerNewDoc.getSize());

               if (landOwnerNewDoc.getType() != null)
                   landOwnerDoc.setType(landOwnerNewDoc.getType());

               if (landOwnerNewDoc.getMime() != null)
                   landOwnerDoc.setMimes(String.join(",", landOwnerNewDoc.getMime()));

               if (landOwnerNewDoc.getParent() != null)
                   landOwnerDoc.setParent(landOwnerNewDoc.getParent());
           }

           landOwnerDocsRepository.save(landOwnerDoc);
       }

    }


    @Override
    public LandOwnerDoc findById(Long id) {
        return landOwnerDocsRepository.findById(id).orElse(null);
    }
    @Override
    public List<LandOwnerDoc> findAll() {
        return (List<LandOwnerDoc>) landOwnerDocsRepository.findAll();
    }
    @Override
    public List<LandOwnerDoc> findByLandOwnerId(Long landOwnerId) {
        return landOwnerDocsRepository.findByLandOwnerId(landOwnerId);
    }

}
