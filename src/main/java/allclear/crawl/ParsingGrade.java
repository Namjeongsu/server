package allclear.crawl;

import allclear.domain.grade.Grade;
import allclear.domain.grade.SemesterGrade;
import allclear.domain.grade.SemesterSubject;

import java.util.ArrayList;

public class ParsingGrade {
    /*
    성적 문자열 파싱함수
    */
    static Grade grade;

    public static Grade parsingGradeString(String totalCredit, String averageGrade,ArrayList<String> entireGrades, ArrayList<String> detailGrades){
        String year; // 학년
        String semester; // 학기
        String semesterAverageGrade; // 학기 평균 학점

        String tmpNum = "64"; // 총 이수학점 임시 점수
        semesterAverageGrade = "";
        grade = new Grade();
        grade.setTotalCredit(Long.parseLong(tmpNum));
        grade.setAverageGrade(averageGrade);
        ArrayList<SemesterSubject> tmpList = new ArrayList<>();

        for(int i=0;i<detailGrades.size();i++){
            if(detailGrades.get(i).contains("*")){ // 학년 학기 문자열이면 *를 포함하고 있음
                if (!tmpList.isEmpty()){
                    grade.addSemesterGrade(SemesterGrade.createSemesterGrade(grade, semesterAverageGrade, tmpList));

                }
                tmpList = new ArrayList<>();
                year = detailGrades.get(i).substring(1, 5); // 해당 성적의 년도 추출
                semester = detailGrades.get(i).substring(9, 11).strip(); // 해당 성적의 학기 추출
                semesterAverageGrade = getSemesterAverageGrade(year,semester,entireGrades);
                // 년도와 학기를 통해 해당 학기 평균 성적 추출
            }
            else{
                tmpList.add(SemesterSubject.createSemesterSubject(detailGrades.get(i+2),detailGrades.get(i+1)));
                // 과목명과 등급을 통해 SemesterSubject 생성
                i = i + 6; // 다음 과목으로 이동

            }
        }
        if (!tmpList.isEmpty()){ // 마지막 학기 추가
            grade.addSemesterGrade(SemesterGrade.createSemesterGrade(grade, semesterAverageGrade, tmpList));
        }

        return grade;
    }

    /*
    평균 성적 추출 함수
     */
    public static String getSemesterAverageGrade(String year, String semester, ArrayList<String> entireGrades){
        String tmpYear = "";
        String tmpSemester = "";
        String semesterAverageGrade = "";

        for(int i=0;i<entireGrades.size()-1;i++){
            if(entireGrades.get(i).isEmpty()) // 값이 비어있다면 값을 가져오지 않음
                continue;
            if(entireGrades.get(i+1).isEmpty()) // 값이 비어있다면 값을 가져오지 않음
                continue;
            if(entireGrades.get(i+5).isEmpty()) // 값이 비어있다면 값을 가져오지 않음
                continue;
            tmpYear = entireGrades.get(i);
            tmpSemester = entireGrades.get(i + 1).substring(0, 2).strip();
            if (tmpYear.equals(year) && tmpSemester.equals((semester))) {
                semesterAverageGrade = entireGrades.get(i + 5);
                break;
            }
        }
        return  semesterAverageGrade;
    }
}
