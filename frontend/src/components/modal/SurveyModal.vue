<template>
  <!-- Modal -->
  <div
    class="modal fade vld-parent"
    id="surveymodal"
    data-backdrop="static"
    data-keyboard="false"
    tabindex="-1"
    aria-labelledby="staticBackdropLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-lg modal-dialog-centered">
      <div class="modal-content" style="text-align:left">
        <div class="modal-header border-0 pl-0" style="height:5rem;">
          <h5 class="modal-title-survey w-100 text-center font-weight-bold position-absolute" id="staticBackdropLabel">
            𝑳𝒊𝒏𝒌𝒊𝒗𝒊𝒕𝒚 Recommend
          </h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <!-- Survey -->
        <div v-if="this.flag == 0 && this.loadFlag == 0" class="modal-body pl-5">
          <div class="form-check-survey">
            <span class="survey-title mr-4">1. 계절 :</span>
            <span class="mr-4">
              <input class="form-check-input-survey mr-1" type="radio" name="seasonRadios" id="seasonRadios1" value="spring" v-model="season" />
              <label class="form-check-label" for="seasonRadios1">봄</label>
            </span>
            <span class="mr-4">
              <input class="form-check-input-survey mr-1" type="radio" name="seasonRadios" id="seasonRadios2" value="summer" v-model="season" />
              <label class="form-check-label" for="seasonRadios2">여름</label>
            </span>
            <span class="mr-4">
              <input class="form-check-input-survey mr-1" type="radio" name="seasonRadios" id="seasonRadios3" value="fall" v-model="season" />
              <label class="form-check-label" for="seasonRadios3">가을</label>
            </span>
            <span>
              <input class="form-check-input-survey mr-1" type="radio" name="seasonRadios" id="seasonRadios4" value="winter" v-model="season" />
              <label class="form-check-label" for="seasonRadios4">겨울</label>
            </span>
          </div>
          
          <div class="form-check-survey">
            <span class="survey-title mr-4">2. 필드 :</span>
            <span class="mr-4">
              <input class="form-check-input-survey mr-1" type="radio" name="fieldRadios" id="fieldRadios1" value="sky" v-model="field" />
              <label class="form-check-label" for="fieldRadios1">하늘</label>
            </span>
            <span class="mr-4">
              <input class="form-check-input-survey mr-1" type="radio" name="fieldRadios" id="fieldRadios2" value="ground" v-model="field" />
              <label class="form-check-label" for="fieldRadios2">땅</label>
            </span>
            <span class="mr-4">
              <input class="form-check-input-survey mr-1" type="radio" name="fieldRadios" id="fieldRadios3" value="water" v-model="field" />
              <label class="form-check-label" for="fieldRadios3">바다</label>
            </span>
          </div>

          <div class="form-check-survey">
            <span class="survey-title mr-4">3. 인원 :</span>
            <span class="mr-4">
              <input class="form-check-input-survey mr-1" type="radio" name="peopleRadios" id="peopleRadios1" value="solo" v-model="people" />
              <label class="form-check-label" for="peopleRadios1">솔로</label>
            </span>
            <span class="mr-4">
              <input class="form-check-input-survey mr-1" type="radio" name="peopleRadios" id="peopleRadios2" value="couple" v-model="people" />
              <label class="form-check-label" for="peopleRadios2">커플</label>
            </span>
            <span class="mr-4">
              <input class="form-check-input-survey mr-1" type="radio" name="peopleRadios" id="peopleRadios3" value="family" v-model="people" />
              <label class="form-check-label" for="peopleRadios3">가족</label>
            </span>
            <span>
              <input class="form-check-input-survey mr-1" type="radio" name="peopleRadios" id="peopleRadios4" value="group" v-model="people" />
              <label class="form-check-label" for="peopleRadios4">단체</label>
            </span>
          </div>

          <div class="form-check-survey">
            <span class="survey-title mr-4">4. 가격 :</span>
            <span class="mr-4">
              <input class="form-check-input-survey mr-1" type="radio" name="priceRadios" id="priceRadios1" value="50000" v-model="price" />
              <label class="form-check-label" for="priceRadios1">5만원 이하</label>
            </span>
            <span class="mr-4">
              <input class="form-check-input-survey mr-1" type="radio" name="priceRadios" id="priceRadios2" value="100000" v-model="price" />
              <label class="form-check-label" for="priceRadios2">10만원 이하</label>
            </span>
            <span class="mr-4">
              <input class="form-check-input-survey mr-1" type="radio" name="priceRadios" id="priceRadios3" value="200000" v-model="price" />
              <label class="form-check-label" for="priceRadios3">20만원 이하</label>
            </span>
          </div>

          <div class="form-check-survey">
            <span class="survey-title mr-4">5. 지역 :</span>
            <span class="mr-4">
              <input class="form-check-input-survey mr-1" type="radio" name="regionRadios" id="regionRadios1" value="서울" v-model="region" />
              <label class="form-check-label" for="regionRadios1">서울</label>
            </span>
            <span class="mr-4">
              <input class="form-check-input-survey mr-1" type="radio" name="regionRadios" id="regionRadios2" value="경기" v-model="region" />
              <label class="form-check-label" for="regionRadios2">경기</label>
            </span>
            <span class="mr-4">
              <input class="form-check-input-survey mr-1" type="radio" name="regionRadios" id="regionRadios3" value="충청" v-model="region" />
              <label class="form-check-label" for="regionRadios3">충청</label>
            </span>
            <span class="mr-4">
              <input class="form-check-input-survey mr-1" type="radio" name="regionRadios" id="regionRadios4" value="강원" v-model="region" />
              <label class="form-check-label" for="regionRadios4">강원</label>
            </span>
            <span class="mr-4">
              <input class="form-check-input-survey mr-1" type="radio" name="regionRadios" id="regionRadios5" value="전라" v-model="region" />
              <label class="form-check-label" for="regionRadios5">전라</label>
            </span>
            <span>
              <input class="form-check-input-survey mr-1" type="radio" name="regionRadios" id="regionRadios6" value="경상" v-model="region" />
              <label class="form-check-label" for="regionRadios6">경상</label>
            </span>
          </div>

        </div>

        <!-- loading -->
        <div class="page container text-center" v-show="loadFlag == 1">
          <div class="page-center" style="margin: 30px;">
            <i class="fa fa-spinner fa-spin fa-9x" style="color:red;"></i>
            <h5 style="margin-top:20px;">Searching...</h5>
          </div>
        </div>

        <!-- Recommend -->
        <div v-if="this.flag == 1 && this.loadFlag == 0" class="modal-body">
          <div class="row text-center">
            <div class="col-md-2"></div>
            <div class="col-md-3"></div>

            <div class="col-md-2">
              <div>
                <i class="fas fa-medal fa-3x" style="color:gold"></i>
              </div>
              <div>
                <h5 class="font-weight-bold" style="margin-top:5px;">{{ surveyResult[0] }}</h5>
              </div>
            </div>

            <div class="col-md-3"></div>
            <div class="col-md-2"></div>
          </div>

          <div class="row text-center">
            <div class="col-md-3"></div>

            <div class="col-md-2">
              <div>
                <i class="fas fa-medal fa-3x" style="color:silver"></i>
              </div>
              <div>
                <h5 class="font-weight-bold" style="margin-top:5px;">{{ surveyResult[1] }}</h5>
              </div>
            </div>

            <div class="col-md-2"></div>

            <div class="col-md-2">
              <div>
                <i class="fas fa-medal fa-3x" style="color:brown"></i>
              </div>
              <div>
                <h5 class="font-weight-bold" style="margin-top:5px;">{{ surveyResult[2] }}</h5>
              </div>
            </div>

            <div class="col-md-3"></div>
          </div>
        </div>

        <!-- Footer -->
        <div class="modal-footer">
          <button type="button" class="btn btn-outline" data-dismiss="modal" @click="changeflag">
            <i class="fas fa-times mr-2"></i>
            취소
          </button>
          <button v-if="this.flag == 0" type="button" class="btn btn-danger" @click.prevent="loading">
            <i class="far fa-hand-point-up mr-2"></i>추천받기
          </button>
          <button v-if="this.flag == 1" type="button" class="btn btn-danger" @click="goPost">
            <i class="far fa-hand-point-up mr-2"></i>상품보러가기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import '../../assets/css/surveymodal.css';
import axios from 'axios';

const baseURL = process.env.VUE_APP_BACKURL;

export default {
  data() {
    return {
      flag: 0,
      season: '',
      field: '',
      people: '',
      price: '',
      region: '',
      surveyResult: [],
      isLoading: false,
      loadFlag: 0,
    };
  },
  methods: {
    loading() {
      this.loadFlag = 1;
      this.isLoading = true;
      setTimeout(() => {
        this.isLoading = false;
        this.recommend();
      }, 2000);
    },
    recommend() {
      if (this.season == '' && this.field == '' && this.people == '' && this.price == '' && this.region == '') {
        alert('항목을 체크해주세요!');
      } else {
        if (this.season == '') {
          alert('계절을 체크해주세요!');
        }
        if (this.field == '') {
          alert('필드을 체크해주세요!');
        }
        if (this.people == '') {
          alert('인원을 체크해주세요!');
        }
        if (this.price == '') {
          alert('가격을 체크해주세요!');
        }
        if (this.region == '') {
          alert('지역을 체크해주세요!');
        }
      }
      if (this.season != '' && this.field != '' && this.people != '' && this.price != '' && this.region != '') {
        this.flag = 1;

        axios
          .get(`${baseURL}/survey/getList/${this.field}/${this.region}/${this.price}/${this.season}`)
          .then((res) => {
            this.surveyResult = res.data;
            this.loadFlag = 0;
          })
          .catch((err) => {
            console.log(err);
          });
      } else {
        this.flag = 0;
        this.loadFlag = 0;
      }
    },
    goPost: function() {
      this.flag = 0;
      this.$router.push('/posts/');
      this.$router.go();
    },
    changeflag() {
      setTimeout(() => {
        this.flag = 0;
        this.loadFlag = 0;
      }, 1000);
    },
  },
};
</script>

<style></style>
