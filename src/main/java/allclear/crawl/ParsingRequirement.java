package allclear.crawl;

import allclear.domain.requirement.Requirement;
import allclear.domain.requirement.RequirementComponent;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class ParsingRequirement {
    static Requirement requirement;
    /*
    졸업요건 문자열 파싱함수
     */
    public static Requirement parsingRequirementString(ArrayList<String> requirementStringList){
        requirement = new Requirement();
        String requirementString;
        String category = "";
        for(int i=0;i<requirementStringList.size();i++){
            if(!requirementStringList.get(i).isEmpty())
                requirementString = requirementStringList.get(i);
            else
                requirementString = "";
            switch (requirementString){
                case "졸업필수 요건" :
                    category = "졸업필수 요건";
                    continue;
                case "교양필수" :
                    category = "교양필수";
                    continue;
                case "교양선택" :
                    category = "교양선택";
                    continue;
                case "전공기초" :
                    category = "전공기초";
                    continue;
                case "전공선택" :
                    category = "전공선택";
                    continue;
                case "전공필수" :
                    category = "전공필수";
                    continue;
                case "전공" :
                    category = "전공";
                    continue;
                case "융합전공선택":
                    category = "융합전공선택";
                    continue;
                case "융합전공필수":
                    category = "융합전공필수";
                    continue;
                case "채플" :
                    category = "채플";
                    continue;
                default:
            }
            RequirementComponent requirementComponent = new RequirementComponent();
            requirementComponent.setRequirementCategory(category); // 이수구분
            if (requirementStringList.get(i).isEmpty()){ // 졸업요건, 값이 비어있는 경우
                requirementComponent.setRequirementArgument("");
            }
            else{
                requirementComponent.setRequirementArgument(requirementStringList.get(i));
            }
            if(requirementStringList.get(i+1).isEmpty()){ // 기준값, 값이 비어있는 경우
                requirementComponent.setRequirementCriteria((Double)null);
            }
            else{
                requirementComponent.setRequirementCriteria(Double.parseDouble(requirementStringList.get(i+1)));
            }
            if (requirementStringList.get(i+2).isEmpty()){ // 계산값, 값이 비어있는 경우
                requirementComponent.setRequirementComplete((Double)null);
            }
            else{
                requirementComponent.setRequirementComplete(Double.parseDouble(requirementStringList.get(i+2)));
            }
            requirementComponent.setRequirementResult(requirementStringList.get(i+4)); // 충족여부
            requirement.addRequirementComponent(requirementComponent); // 졸업요건 행들을 모으기
            assert requirement != null;
            i = i +4;
        }
        return requirement;
    }
}
