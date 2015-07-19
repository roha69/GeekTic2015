angular.module('geekticApp', ['frontendServices', 'spring-security-csrf-token-interceptor', 'angularRoutes'])
        .filter('excludeDeleted', function () {
            return function (input) {
                return _.filter(input, function (item) {
                    return item.deleted === undefined || !item.deleted;
                });
            };
        })
        .controller('GeekticCtrl', ['$scope', 'ProfileService', 'UserService', '$timeout',
            function ($scope, ProfileService, UserService, $timeout) {

                
                $scope.currentPage = 1;
                $scope.totalPages = 0;
                $scope.originalProfiles = [];
                $scope.profiles = [];
                $scope.isSelectionEmpty = true;
                $scope.errorMessages = [];
                $scope.infoMessages = [];

                updateUserInfo();
                markAppAsInitialized();
                //loadProfileData(null, null, null, null, 1);


                function showErrorMessage(errorMessage) {
                    clearMessages();
                    $scope.errorMessages.push({description: errorMessage});
                }

                function updateUserInfo() {
                    UserService.getUserInfo()
                            .then(function (userInfo) {
                                $scope.userName = userInfo.userName;
                            },
                                    function (errorMessage) {
                                        showErrorMessage(errorMessage);
                                    });
                }

                /**
                 * Data are loaded
                 * Mark app ready
                 */
                function markAppAsInitialized() {
                    if ($scope.appReady === undefined) {
                        $scope.appReady = true;
                    }
                }

                /**
                 * 
                 * @param {type} fromDate
                 * @param {type} fromTime
                 * @param {type} toDate
                 * @param {type} toTime
                 * @param {type} pageNumber
                 * @returns {undefined}
                 */
                function loadProfileData(fromDate, fromTime, toDate, toTime, pageNumber) {
                    markAppAsInitialized();
                    /*
                     ProfileService.searchProfiles(gender, interests, pageNumber)
                     .then(function (data) {
                     
                     $scope.errorMessages = [];
                     $scope.currentPage = data.currentPage;
                     $scope.totalPages = data.totalPages;
                     
                     $scope.originalMeals = _.map(data.meals, function (meal) {
                     meal.datetime = meal.date + ' ' + meal.time;
                     return meal;
                     });
                     
                     $scope.meals = _.cloneDeep($scope.originalMeals);
                     
                     _.each($scope.meals, function (meal) {
                     meal.selected = false;
                     });
                     
                     markAppAsInitialized();
                     
                     if ($scope.meals && $scope.meals.length == 0) {
                     showInfoMessage("No results found.");
                     }
                     },
                     function (errorMessage) {
                     showErrorMessage(errorMessage);
                     markAppAsInitialized();
                     });*/
                }

                function clearMessages() {
                    $scope.errorMessages = [];
                    $scope.infoMessages = [];
                }

                function showInfoMessage(infoMessage) {
                    $scope.infoMessages = [];
                    $scope.infoMessages.push({description: infoMessage});
                    $timeout(function () {
                        $scope.infoMessages = [];
                    }, 1000);
                }

                $scope.updateMaxCaloriesPerDay = function () {
//                $timeout(function () {
//
//                    if ($scope.maxCaloriesPerDay < 0) {
//                        return;
//                    }
//
//                    UserService.updateMaxCaloriesPerDay($scope.maxCaloriesPerDay)
//                        .then(function () {
//                        },
//                        function (errorMessage) {
//                            showErrorMessage(errorMessage);
//                        });
//                    updateCaloriesCounterStatus();
//                });
                };

                $scope.pages = function () {
                    return _.range(1, $scope.totalPages + 1);
                };

                $scope.search = function (page) {

                    var fromDate = new Date($scope.fromDate);
                    var toDate = new Date($scope.toDate);

                    console.log('search from ' + $scope.fromDate + ' ' + $scope.fromTime + ' to ' + $scope.toDate + ' ' + $scope.toTime);

                    var errorsFound = false;

                    if ($scope.fromDate && !$scope.toDate || !$scope.fromDate && $scope.toDate) {
                        showErrorMessage("Both from and to dates are needed");
                        errorsFound = true;
                        return;
                    }

                    if (fromDate > toDate) {
                        showErrorMessage("From date cannot be larger than to date");
                        errorsFound = true;
                    }

                    if (fromDate.getTime() == toDate.getTime() && $scope.fromTime &&
                            $scope.toTime && $scope.fromTime > $scope.toTime) {
                        showErrorMessage("Inside same day, from time cannot be larger than to time");
                        errorsFound = true;
                    }

                    if (!errorsFound) {
                        loadProfileData($scope.fromDate, $scope.fromTime, $scope.toDate, $scope.toTime, page == undefined ? 1 : page);
                    }

                };

                $scope.previous = function () {
                    if ($scope.currentPage > 1) {
                        $scope.currentPage -= 1;
                        loadProfileData($scope.fromDate, $scope.fromTime,
                                $scope.toDate, $scope.toTime, $scope.currentPage);
                    }
                };

                $scope.next = function () {
                    if ($scope.currentPage < $scope.totalPages) {
                        $scope.currentPage += 1;
                        loadProfileData($scope.fromDate, $scope.fromTime,
                                $scope.toDate, $scope.toTime, $scope.currentPage);
                    }
                };

                $scope.goToPage = function (pageNumber) {
                    if (pageNumber > 0 && pageNumber <= $scope.totalPages) {
                        $scope.currentPage = pageNumber;
                        loadProfileData($scope.fromDate, $scope.fromTime, $scope.toDate, $scope.toTime, pageNumber);
                    }
                };

                $scope.add = function () {
                    $scope.meals.unshift({
                        id: null,
                        datetime: null,
                        description: null,
                        calories: null,
                        selected: false,
                        new : true
                    });
                };

                $scope.delete = function () {
                    var deletedMealIds = _.chain($scope.meals)
                            .filter(function (meal) {
                                return meal.selected && !meal.new;
                            })
                            .map(function (meal) {
                                return meal.id;
                            })
                            .value();

                    ProfileService.deleteMeals(deletedMealIds)
                            .then(function () {
                                clearMessages();
                                showInfoMessage("deletion successful.");

                                _.remove($scope.meals, function (meal) {
                                    return meal.selected;
                                });

                                $scope.selectionChanged();
                                updateUserInfo();

                            },
                                    function () {
                                        clearMessages();
                                        $scope.errorMessages.push({description: "deletion failed."});
                                    });
                };

                $scope.reset = function () {
                    $scope.meals = $scope.originalMeals;
                };

                function getNotNew(meals) {
                    return  _.chain(meals)
                            .filter(function (meal) {
                                return !meal.new;
                            })
                            .value();
                }

                function prepareMealsDto(meals) {
                    return  _.chain(meals)
                            .each(function (meal) {
                                if (meal.datetime) {
                                    var dt = meal.datetime.split(" ");
                                    meal.date = dt[0];
                                    meal.time = dt[1];
                                }
                            })
                            .map(function (meal) {
                                return {
                                    id: meal.id,
                                    date: meal.date,
                                    time: meal.time,
                                    description: meal.description,
                                    calories: meal.calories,
                                    version: meal.version
                                }
                            })
                            .value();
                }

                $scope.save = function () {

                    var maybeDirty = prepareMealsDto(getNotNew($scope.meals));

                    var original = prepareMealsDto(getNotNew($scope.originalMeals));

                    var dirty = _.filter(maybeDirty).filter(function (meal) {

                        var originalMeal = _.filter(original, function (orig) {
                            return orig.id === meal.id;
                        });

                        if (originalMeal.length == 1) {
                            originalMeal = originalMeal[0];
                        }

                        return originalMeal && (originalMeal.date != meal.date ||
                                originalMeal.time != meal.time || originalMeal.description != meal.description ||
                                originalMeal.calories != meal.calories)
                    });

                    var newItems = _.filter($scope.meals, function (meal) {
                        return meal.new;
                    });

                    var saveAll = prepareMealsDto(newItems);
                    saveAll = saveAll.concat(dirty);

                    $scope.errorMessages = [];

                    // save all new items plus the ones that where modified
                    ProfileService.saveMeals(saveAll).then(function () {
                        $scope.search($scope.currentPage);
                        showInfoMessage("Changes saved successfully");
                        updateUserInfo();
                    },
                            function (errorMessage) {
                                showErrorMessage(errorMessage);
                            });

                };

                $scope.logout = function () {
                    UserService.logout();
                }

            }]);

