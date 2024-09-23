package com.bookpago.readingclub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadingClubMapService {

//    private final ReadingClubMapRepository readingClubMapRepository;
//
//    public Page<ReadingClubDto> getUserClubs(Profile profile, int page, int size) {
//        // 관리자와 멤버로 검색하여 리스트 가져오기
//        System.out.println(profile.getId());
//        List<ReadingClubMap> adminClubs = readingClubMapRepository.findByClubAdmin(profile);
//        List<ReadingClubMap> memberClubs = readingClubMapRepository.findByClubMember(profile);
//
//        // 두 리스트를 합치기 위해 새로운 리스트 생성
//        List<ReadingClubDto> readingClubDtoList = new ArrayList<>();
//
//        // adminClubs 리스트를 Dto로 변환 후 추가
//        readingClubDtoList.addAll(adminClubs.stream()
//                .map(club -> new ReadingClubDto(club.getReadingClub().getClubName(),
//                        club.getReadingClub().getDescription(), club.getReadingClub().getLocation(),
//                        club.getReadingClub().getDescription()))
//                .collect(Collectors.toList()));
//
//        // memberClubs 리스트를 Dto로 변환 후 추가
//        readingClubDtoList.addAll(memberClubs.stream()
//                .map(club -> new ReadingClubDto(club.getReadingClub().getClubName(),
//                        club.getReadingClub().getDescription(), club.getReadingClub().getLocation(),
//                        club.getReadingClub().getDescription()))
//                .collect(Collectors.toList()));
//
//        // 페이징 정보 설정
//        Pageable pageable = PageRequest.of(page, size);
//        int start = (int) pageable.getOffset();
//        int end = Math.min((start + pageable.getPageSize()), readingClubDtoList.size());
//
//        // 유효한 범위 내의 결과 리스트만 추출
//        List<ReadingClubDto> content = (start > readingClubDtoList.size())
//                ? new ArrayList<>()
//                : readingClubDtoList.subList(start, end);
//        System.out.println(content.size());
//
//        // 페이징된 결과 리스트 반환
//        return new PageImpl<>(content);
//    }

}
